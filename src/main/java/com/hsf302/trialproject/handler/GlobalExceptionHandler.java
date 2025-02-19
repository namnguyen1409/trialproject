package com.hsf302.trialproject.handler;

import com.hsf302.trialproject.exception.Http400;
import com.hsf302.trialproject.exception.Http404;
import com.hsf302.trialproject.exception.Http500;
import com.hsf302.trialproject.security.CustomUserDetails;
import com.hsf302.trialproject.enums.MessageTypeEnum;
import com.hsf302.trialproject.enums.ViewEnum;
import com.hsf302.trialproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getUser();
        }
        return null;
    }

    private void addCurrentUser(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
    }


    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleHttp400(Model model) {
        addCurrentUser(model);
        model.addAttribute(MessageTypeEnum.ERROR.getType(),
                new Error("400",
                        "Lỗi yêu cầu",
                        "Xin lỗi, yêu cầu của bạn không hợp lệ."));
        return ViewEnum.ERROR.getViewName();
    }



    @ExceptionHandler({NoResourceFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleHttp404(Model model) {
        addCurrentUser(model);
        model.addAttribute(MessageTypeEnum.ERROR.getType(),
                new Error("404",
                        "Ôi không tìm thấy trang này",
                        "Xin lỗi, trang bạn đang tìm kiếm không tồn tại hoặc đã bị xóa."));
        return ViewEnum.ERROR.getViewName();
    }

    @ExceptionHandler(Http400.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleHttp400(Http400 exception,
                                Model model) {
        addCurrentUser(model);
        model.addAttribute(MessageTypeEnum.ERROR.getType(),
                new Error("400",
                        "Lỗi yêu cầu",
                        exception.getMessage()));
        return ViewEnum.ERROR.getViewName();
    }


    @ExceptionHandler(Http404.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleHttp404(Http404 exception,
                                Model model) {
        addCurrentUser(model);
        model.addAttribute(MessageTypeEnum.ERROR.getType(),
                new Error("404",
                        "Không tìm thấy!!!",
                        exception.getMessage()));
        return ViewEnum.ERROR.getViewName();
    }


    @ExceptionHandler(Http500.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleHttp500(Http500 exception,
                                Model model) {
        addCurrentUser(model);
        model.addAttribute(MessageTypeEnum.ERROR.getType(),
                new Error("500",
                        "Lỗi hệ thống",
                        exception.getMessage()));
        return ViewEnum.ERROR.getViewName();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception exception,
                                         Model model) {
        addCurrentUser(model);
        model.addAttribute(MessageTypeEnum.ERROR.getType(),
                new Error("500",
                        "Lỗi hệ thống",
                        "Xin lỗi, đã xảy ra lỗi hệ thống. Vui lòng thử lại sau."));
        System.out.println(exception.getMessage());
        return ViewEnum.ERROR.getViewName();
    }

    @Data
    @AllArgsConstructor
    static class Error {
        private String code;
        private String title;
        private String message;

    }
}