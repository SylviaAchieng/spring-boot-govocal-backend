package com.example.engagement_platform.service;

import com.example.engagement_platform.enums.EmailTemplateName;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

import java.io.UnsupportedEncodingException;

public interface EmailService {


    void sendSimpleMessage(String to, String subject, String text);

    void sendEmail(String to, String subject, String text, String replyToEmail, String emailFromUsername) throws MessagingException, UnsupportedEncodingException;

    void sendMessageWithAttachment(
            String to, String subject, String text, String pathToAttachment) throws MessagingException;

    void sendSimpleMessage(String email, String fullName, EmailTemplateName emailTemplateName, String activationUrl, String newToken, String accountActivation);
}
