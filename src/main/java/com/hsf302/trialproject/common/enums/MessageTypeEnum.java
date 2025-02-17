package com.hsf302.trialproject.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageTypeEnum {
    SUCCESS("success"),
    INFO("info"),
    WARNING("warning"),
    ERROR("error");

    private final String type;
}
