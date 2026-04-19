package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String subject, String message, String to) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(message);
            // mail.setContent(message,"text/html")
            mail.setFrom("vivekumar0709@gmail.com");

            mailSender.send(mail);

            System.out.println("✅ Email sent successfully");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
