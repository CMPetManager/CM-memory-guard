package com.catchthemoment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConfirmMailService {
    private final JavaMailSender mailSender;

    public String sendConfirmationEmail(String email) {
        SimpleMailMessage mailMessage = createConfiramtionMail(email);
        mailSender.send(mailMessage);
//todo return confirmation token
        return null;
    }

    private SimpleMailMessage createConfiramtionMail(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + createConfirmationToken());
        return mailMessage;
    }

    private String createConfirmationToken() {
//        todo
        return null;
    }
}
