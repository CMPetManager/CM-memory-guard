package com.catchthemoment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.protocol}")
    private String protocol;
    @Value("${spring.mail.port}")
    private String port;
    @Value("${mail.debug}")
    private String debug;

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = mailSender.getJavaMailProperties();
        // Email protocol
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);
        properties.put("mail.smtp.port", port); //TLS Port
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("spring.mail.properties.mail.smtp.ssl.enable", "true");
        return mailSender;

    }
}
