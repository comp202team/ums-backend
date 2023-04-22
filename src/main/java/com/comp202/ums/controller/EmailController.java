package com.comp202.ums.controller;

import com.comp202.ums.Dto.email.EmailMessage;
import com.comp202.ums.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mail")
public class EmailController {
    private EmailService emailService;
    public EmailController(EmailService emailService){
        this.emailService=emailService;
    }
    @PostMapping("/send")
    public ResponseEntity<?> SendEmail(@RequestBody EmailMessage mail){
        emailService.sendSimpleMessage(mail.getEmail(), mail.getSubject(), mail.getMessage());
        return ResponseEntity.ok().build();
    }

}
