package com.catchthemoment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceMail{

    private final JavaMailSender mailSender;

    @Async
    public void sendMail(SimpleMailMessage mailMessage){
        mailSender.send(mailMessage);
    }
}
