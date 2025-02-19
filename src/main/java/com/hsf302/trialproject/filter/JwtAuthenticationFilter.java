package com.hsf302.trialproject.filter;

import com.hsf302.trialproject.security.CustomUserDetails;
import com.hsf302.trialproject.security.JwtTokenProvider;
import com.hsf302.trialproject.security.RefreshTokenProvider;
import com.hsf302.trialproject.service.CustomUserDetailsService;
import com.hsf302.trialproject.enums.TokenEnum;
import com.hsf302.trialproject.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.login.AccountLockedException;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    private RefreshTokenProvider refreshTokenProvider;

    private CustomUserDetailsService customUserDetailsService;

    private MessageSource messageSource;

    private CookieUtil cookieUtil;


    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request     the current request
     * @param response    the current response
     * @param filterChain the current filter chain
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            if (isErrorRequest(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            if (authenticateWithJwt(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            if (authenticateWithRefreshToken(request, response)) {
                filterChain.doFilter(request, response);
                return;
            }

            SecurityContextHolder.clearContext();
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Kiểm tra request có phải là request lỗi không
     * @param request
     * @return true nếu request là request lỗi
     */
    private boolean isErrorRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith(request.getContextPath() + "/error");
    }

    private boolean authenticateWithJwt(HttpServletRequest request) throws AccountLockedException {
        var jwt = cookieUtil.getCookie(request, TokenEnum.JWT.getTokenName());
        if (!StringUtils.hasText(jwt) || !jwtTokenProvider.validateToken(jwt)) {
            return false;
        }

        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        var userDetails = customUserDetailsService.loadUserById(userId);
        return setAuthentication(userDetails, request);
    }

    private boolean authenticateWithRefreshToken(HttpServletRequest request, HttpServletResponse response) throws AccountLockedException {
        String refreshToken = cookieUtil.getCookie(request, TokenEnum.REFRESH.getTokenName());
        if (StringUtils.hasText(refreshToken) && refreshTokenProvider.validateRefreshToken(refreshToken)) {
            var key = refreshTokenProvider.getKeyFromRefreshToken(refreshToken);
            var userDetails = customUserDetailsService.loadUserByRefreshToken(key);
            if (setAuthentication(userDetails, request)) {
                addJwtCookieToResponse(response, jwtTokenProvider.generateToken(userDetails));
                return true;
            }
        }
        return false;
    }

    private boolean setAuthentication(UserDetails userDetails, HttpServletRequest request) throws AccountLockedException {
        if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (!userDetails.isAccountNonLocked()) {
                throw new AccountLockedException(getLockMessage(userDetails, request));
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        }
        return false;
    }

    private String getLockMessage(UserDetails userDetails, HttpServletRequest request) {
        return messageSource.getMessage("user.locked", new Object[]{((CustomUserDetails) userDetails).getUser().getLockReason()}, request.getLocale());
    }

    private void addJwtCookieToResponse(HttpServletResponse response, String newJwtToken) {
        Cookie jwtCookie = new Cookie("jwtToken", newJwtToken);
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge((int) (Long.parseLong(jwtTokenProvider.getExpiration()) / 1000L));
        response.addCookie(jwtCookie);
    }


}
