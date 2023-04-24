package com.comp202.ums.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender emailSender;
    public EmailService(JavaMailSender emailSender){
        this.emailSender=emailSender;
    }

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@univesity.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }
    public void sendInvitationEmail (String to,String link, String courseName){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@univesity.com");
        message.setTo(to);
        message.setSubject("Invitation to the course");
        message.setText("You have been invited to the"+courseName+"\n"+link);
        emailSender.send(message);

    }
}
