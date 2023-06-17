package com.catchthemoment.service;

import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ApplicationErrorEnum;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserConfirmMailService {
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String mailAddress;
    @Value("${spring.application.name}")
    private String sender;
    @Value("application.url")
    private String urlValue;

    private final static String URL_VERIFY = "/users/verify?code= ";

    public void verifyAccount(@NotNull String token) throws ServiceProcessingException {
        User user = userRepository.findUSerByConfirmationResetToken(token).
                orElseThrow(() -> new ServiceProcessingException(ApplicationErrorEnum.USER_NOT_FOUND));
        user.setConfirmationResetToken(null);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void sendVerificationEmail(User user, String urlValue)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = mailAddress;
        String senderName = sender;
        String subject = "Please verify your email";
        String content = "Dear [[name]],<br>"
                + "Thank you for registering on our website. Your account has been created and is now ready for use."
                + "To complete your registration and activate your account, please click on the following link:" +
                ":<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "If you are unable to click the link, please copy and paste it into your web browser's address bar.\n" +
                "Thank you for choosing our platform. We hope you enjoy using our services.\n" +
                "Best regards,\n" +
                "Catch The Moment Team<br>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", user.getName());
        String verifyURL = urlValue + URL_VERIFY + user.getConfirmationResetToken();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);
        mailSender.send(message);

    }
}
