package com.hsf302.trialproject.common.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
public class GlobalPageTitleAdvice {
    private final MessageSource messageSource;
    private final Map<String, String> pageTitles = Map.of(
            "/home", "common.title.home",
        "/login", "auth.title.login",
        "/register", "auth.title.register",
        "/change-password", "auth.title.change-password"
    );

    @ModelAttribute("pageTitle")
    public String getPageTitle(HttpServletRequest request) {
        var uri = request.getRequestURI();
        var messageKey = pageTitles.getOrDefault(uri, "common.title.default");
        return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
    }
}
