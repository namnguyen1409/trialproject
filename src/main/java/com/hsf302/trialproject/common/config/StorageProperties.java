package com.hsf302.trialproject.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("storage")
public class StorageProperties {

    @Value("${file.upload-dir}")
    private String location;

    @Value("${file.temp-dir}")
    private String tempLocation;

}
