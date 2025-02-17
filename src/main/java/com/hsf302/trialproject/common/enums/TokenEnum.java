package com.hsf302.trialproject.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenEnum {
    JWT("jwtToken"),
    REFRESH("refreshToken");
    private final String tokenName;
}
