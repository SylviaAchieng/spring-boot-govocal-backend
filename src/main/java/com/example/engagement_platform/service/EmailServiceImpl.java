package com.example.engagement_platform.service;

import com.example.engagement_platform.enums.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Component
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender emailSender;

    @Value("spring.mail.username")
    private String emailFrom;
    @Override
    public void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendEmail(String to, String subject, String text, String replyToEmail, String emailFromUsername) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = getMimeMessageHelper(
                mimeMessage,
                new String[]{to},
                new String[]{},
                new String[]{},
                subject,
                text,
                emailFromUsername,
                replyToEmail);
        emailSender.send(mimeMessage);
    }

    private MimeMessageHelper getMimeMessageHelper(MimeMessage mimeMessage,
                                                   String[] emailTo,
                                                   String[] emailCC,
                                                   String[] emailBCC,
                                                   String subject,
                                                   String emailBody,
                                                   String emailFrom_Username,
                                                   String replyToEmail) throws MessagingException, UnsupportedEncodingException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        // set to address when not null or empty
        if (emailTo == null || emailTo.length < 1)
            throw new RuntimeException("To address cannot be empty or null");
        else
            mimeMessageHelper.setTo(emailTo);

        // set cc address if not null or empty
        if (emailCC != null && emailCC.length > 0)
            mimeMessageHelper.setCc(emailCC);


        // set bcc address when not null or empty
        if (emailBCC != null && emailBCC.length > 0)
            mimeMessageHelper.setBcc(emailBCC);

        // set from
        if (emailFrom_Username != null)
            mimeMessageHelper.setFrom(emailFrom, emailFrom_Username);
        else {
            mimeMessageHelper.setFrom(emailFrom, "GOK");
        }


        // set subject
        mimeMessageHelper.setSubject(subject);

        // set body
        mimeMessageHelper.setText(emailBody, true);

        if (replyToEmail == null || replyToEmail.isEmpty() || replyToEmail.isBlank()){
            mimeMessageHelper.setReplyTo(emailFrom);
        }else {
            mimeMessageHelper.setReplyTo(replyToEmail);
        }

        return mimeMessageHelper;
    }

    @Override
    public void sendMessageWithAttachment(
            String to, String subject, String text, String pathToAttachment) throws MessagingException {


        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(emailFrom);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        FileSystemResource file
                = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Invoice", file);

        emailSender.send(message);

    }

    @Override
    public void sendSimpleMessage(String email, String fullName, EmailTemplateName emailTemplateName, String activationUrl, String newToken, String accountActivation) {

    }
}
