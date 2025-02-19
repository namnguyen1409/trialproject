package com.hsf302.trialproject.controller;

import com.hsf302.trialproject.dto.ChangePasswordDTO;
import com.hsf302.trialproject.dto.LoginDTO;
import com.hsf302.trialproject.dto.RegisterDTO;
import com.hsf302.trialproject.exception.Http400;
import com.hsf302.trialproject.exception.Http404;
import com.hsf302.trialproject.security.CustomUserDetails;
import com.hsf302.trialproject.security.JwtTokenProvider;
import com.hsf302.trialproject.security.RefreshTokenProvider;
import com.hsf302.trialproject.service.MessageService;
import com.hsf302.trialproject.service.RecaptchaService;
import com.hsf302.trialproject.enums.MessageKeyEnum;
import com.hsf302.trialproject.enums.MessageTypeEnum;
import com.hsf302.trialproject.enums.TokenEnum;
import com.hsf302.trialproject.enums.ViewEnum;
import com.hsf302.trialproject.service.StorageService;
import com.hsf302.trialproject.util.CookieUtil;
import com.hsf302.trialproject.util.EncryptionUtil;
import com.hsf302.trialproject.entity.RefreshToken;
import com.hsf302.trialproject.entity.RegistrationToken;
import com.hsf302.trialproject.entity.User;
import com.hsf302.trialproject.repository.RefreshTokenRepository;
import com.hsf302.trialproject.repository.RegistrationTokenRepository;
import com.hsf302.trialproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
@Data
@Transactional
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final RecaptchaService recaptchaService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RegistrationTokenRepository registrationTokenRepository;
    private final EncryptionUtil encryptionUtil;
    private final CookieUtil cookieUtil;
    private final StorageService storageService;
    private final MessageService messageService;

    @Value("${refresh.token.expiration}")
    private String refreshTokenExpiration;

    @Value("${jwt.expiration}")
    private String jwtTokenExpiration;

    private User getUser() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

    private void addFlashMessage(RedirectAttributes redirectAttributes, MessageKeyEnum messageKey, MessageTypeEnum messageType) {
        redirectAttributes.addFlashAttribute("flashMessage", messageService.getMessage(messageKey.getKey()));
        redirectAttributes.addFlashAttribute("flashMessageType", messageType.getType());
    }

    private boolean notValidateRecaptcha(String recaptchaResponse, BindingResult bindingResult) {
        if (recaptchaService.notVerifyRecaptcha(recaptchaResponse)) {
            bindingResult.rejectValue("recaptchaResponse", MessageKeyEnum.ERROR_RECAPTCHA.getKey());
            return true;
        }
        return false;
    }

    private RegistrationToken validateRegistrationToken(String token) throws Exception {
        var registrationToken = registrationTokenRepository.findByToken(encryptionUtil.encrypt(token));
        if (registrationToken == null) {
            throw new Http404(messageService.getMessage(MessageKeyEnum.ERROR_REGISTER_TOKEN_INVALID.getKey()));
        }
        if (registrationToken.getUpdatedAt().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new Http400(messageService.getMessage(MessageKeyEnum.ERROR_REGISTER_TOKEN_EXPIRED.getKey()));
        }
        return registrationToken;
    }



    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return ViewEnum.LOGIN.getViewName();
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("loginDTO") @Validated LoginDTO loginDTO,
            BindingResult bindingResult,
            @RequestParam("g-recaptcha-response") String recaptchaResponse
    ) {
        if (notValidateRecaptcha(recaptchaResponse, bindingResult)) {
            return ViewEnum.LOGIN.getViewName();
        }

        if (bindingResult.hasErrors()) {
            return ViewEnum.LOGIN.getViewName();
        }
        Optional<User> userOptional = userRepository.findByUsername(loginDTO.getUsername());
        if (!passwordEncoder.matches(loginDTO.getPassword(), userOptional.get().getPassword())) {
            bindingResult.rejectValue("password", MessageKeyEnum.ERROR_LOGIN_PASSWORD.getKey());
            return ViewEnum.LOGIN.getViewName();
        }
        var user = userOptional.get();
        var jwt = jwtTokenProvider.generateToken(new CustomUserDetails(user));
        if (Boolean.TRUE.equals(loginDTO.getRemember())) {
            var refreshToken = refreshTokenProvider.generateRefreshToken(UUID.randomUUID().toString());
            RefreshToken refreshTokenEntity = new RefreshToken();
            refreshTokenEntity.setToken(refreshTokenProvider.getKeyFromRefreshToken(refreshToken));
            refreshTokenEntity.setUser(user);
            refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusSeconds(Long.parseLong(refreshTokenExpiration)));
            refreshTokenRepository.save(refreshTokenEntity);
            cookieUtil.addCookie(TokenEnum.JWT.getTokenName(), jwt, Integer.parseInt(Long.parseLong(jwtTokenExpiration) / 1000L + ""), "/", true, false);
            cookieUtil.addCookie(TokenEnum.REFRESH.getTokenName(), refreshToken, Integer.parseInt(Long.parseLong(refreshTokenExpiration) / 1000L + ""), "/", true, false);
        } else {
            cookieUtil.addCookie(TokenEnum.JWT.getTokenName(), jwt, Integer.parseInt(Long.parseLong(jwtTokenExpiration) / 1000L + ""), "/", true, false);
        }
        return "redirect:/";
    }


    @GetMapping("/register")
    public String register(
            @RequestParam("token") String token,
            Model model
    ) throws Exception {
        validateRegistrationToken(token);
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setToken(token);
        model.addAttribute("registerDTO", registerDTO);
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @Validated @ModelAttribute("registerDTO") RegisterDTO registerDTO,
            BindingResult bindingResult,
            @RequestParam("g-recaptcha-response") String recaptchaResponse
    ) throws Exception {
        if (notValidateRecaptcha(recaptchaResponse, bindingResult)) {
            return ViewEnum.REGISTER.getViewName();
        }
        if (bindingResult.hasErrors()) {
            return ViewEnum.REGISTER.getViewName();
        }
        var registrationToken = validateRegistrationToken(registerDTO.getToken());
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setPhone(registerDTO.getPhone());
        user.setGender(registerDTO.getGender());
        user.setBirthday(registerDTO.getBirthday());
        user.setAddress(registerDTO.getAddress());
        user.setEmail(registrationToken.getEmail());
        user.setRole(registrationToken.getRole());
        user.setOwner(registrationToken.getCreatedBy());
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(

    ) {
        var refreshToken = cookieUtil.getCookie(TokenEnum.REFRESH.getTokenName());
        if (refreshToken != null) {
            refreshTokenRepository.deleteByToken(refreshTokenProvider.getKeyFromRefreshToken(refreshToken));
        }
        cookieUtil.deleteCookie(TokenEnum.JWT.getTokenName(), "/", true, false);
        cookieUtil.deleteCookie(TokenEnum.REFRESH.getTokenName(), "/", true, false);
        return "redirect:/login";
    }

    @GetMapping("profile")
    public String profile() {
        return ViewEnum.PROFILE.getViewName();
    }

    @PostMapping("profile")
    public String profile(@RequestParam("avatar") String avatar,
                          @RequestParam("g-recaptcha-response") String recaptchaResponse,
                          RedirectAttributes redirectAttributes
    ) {
        if (recaptchaService.notVerifyRecaptcha(recaptchaResponse)) {
            addFlashMessage(redirectAttributes, MessageKeyEnum.ERROR_RECAPTCHA, MessageTypeEnum.ERROR);
        } else {
            User user = getUser();
            if (user.getAvatar() != null) {
                storageService.deleteFile(user.getAvatar());
            }
            user.setAvatar(avatar);
            userRepository.save(user);
            addFlashMessage(redirectAttributes, MessageKeyEnum.SUCCESS_UPDATE_AVATAR, MessageTypeEnum.SUCCESS);
        }

        return "redirect:/profile";
    }

    @GetMapping("change-password")
    public String changePassword(Model model) {
        model.addAttribute("changePasswordDTO", new ChangePasswordDTO());
        return ViewEnum.CHANGE_PASSWORD.getViewName();
    }

    @PostMapping("change-password")
    public String changePassword(
            @Validated @ModelAttribute("changePasswordDTO") ChangePasswordDTO changePasswordDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return ViewEnum.CHANGE_PASSWORD.getViewName();
        }
        User user = getUser();
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            bindingResult.rejectValue("oldPassword", MessageKeyEnum.ERROR_CHANGE_PASSWORD_OLD.getKey());
            return ViewEnum.CHANGE_PASSWORD.getViewName();
        }
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
        userRepository.save(user);
        addFlashMessage(redirectAttributes, MessageKeyEnum.SUCCESS_CHANGE_PASSWORD, MessageTypeEnum.SUCCESS);
        return "redirect:/change-password";
    }


}
