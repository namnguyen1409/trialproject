package com.hsf302.trialproject.auth.config;

import com.hsf302.trialproject.user.entity.User;
import com.hsf302.trialproject.user.enums.RoleType;
import com.hsf302.trialproject.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final UserRepository userRepository;
    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${admin.firstname}")
    private String adminFirstname;
    @Value("${admin.lastname}")
    private String adminLastname;
    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.phone}")
    private String adminPhone;
    @Value("${admin.gender}")
    private Boolean adminGender;
    @Value("${admin.birthday}")
    private String adminBirthday;
    @Value("${admin.address}")
    private String adminAddress;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            User admin = new User();
            admin.setUsername(adminUsername);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(adminPassword);
            admin.setPassword(encodedPassword);
            admin.setRole(RoleType.ADMIN);
            admin.setFirstName(adminFirstname);
            admin.setLastName(adminLastname);
            admin.setEmail(adminEmail);
            admin.setPhone(adminPhone);
            admin.setGender(adminGender);
            admin.setBirthday(LocalDate.parse(adminBirthday));
            admin.setAddress(adminAddress);
            userRepository.save(admin);
            logger.info("Admin created");
        } else {
            logger.info("Admin already exists");
        }
    }
}
