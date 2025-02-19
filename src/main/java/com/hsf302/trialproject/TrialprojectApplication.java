package com.hsf302.trialproject;

import com.hsf302.trialproject.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EntityScan("com.hsf302.trialproject")
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class TrialprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrialprojectApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }

}
