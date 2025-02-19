package com.hsf302.trialproject.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

        void sendMail(String to, String subject, String body);

        boolean sendHTMLMail(String to, String subject, String body);
}
