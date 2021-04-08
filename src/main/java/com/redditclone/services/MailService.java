package com.redditclone.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendMail(String to, String mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(mail, true);
            helper.setTo(to);
            helper.setSubject("Activate your Account");
            helper.setFrom("ahmednabil@reddit-clone.com");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("failed to send email");
        }
    }
}
