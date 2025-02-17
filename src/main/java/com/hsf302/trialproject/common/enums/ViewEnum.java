package com.hsf302.trialproject.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ViewEnum {

    LOGIN("auth/login"),
    REGISTER("auth/register"),
    PROFILE("auth/profile"),
    CHANGE_PASSWORD("auth/change-password"),
    FORGOT_PASSWORD("auth/forgot-password"),
    ERROR("common/error"),
    HOME("common/home"),
    ;
    private final String viewName;
}
