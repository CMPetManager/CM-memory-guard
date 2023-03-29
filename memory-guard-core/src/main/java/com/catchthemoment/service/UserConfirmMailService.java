package com.catchthemoment.service;

import com.catchthemoment.dto.UserDTO;
import com.catchthemoment.entity.User;
import com.catchthemoment.exception.VerifyAccountException;
import com.catchthemoment.mappers.UserMapper;
import com.catchthemoment.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor

public class UserConfirmMailService {
    private final JavaMailSender mailSender;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${spring.mail.username}")
    private String mailAddress;
    @Value("${spring.application.name}")
    private String sender;


    public void register(UserDTO userDTO, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = encoder.encode(userDTO.password());
        User user = userMapper.fromUserDTO(userDTO);
        user.setPassword(encodedPassword);

        String randomCode = RandomString.make(20);
        user.setConfirmationResetToken(randomCode);
        userRepository.save(user);
        sendVerificationEmail(user, siteURL);

    }

    public boolean verifyAccount(@NotNull String token) throws VerifyAccountException {
        User user = userRepository.findUSerByConfirmationResetToken(token).
                orElseThrow(() -> new VerifyAccountException(505, "Invalid token"));
        user.setConfirmationResetToken(null);
        userRepository.save(user);
        return true;
    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = mailAddress;
        String senderName = sender;
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "CatchTheMomentTeam.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName());
        String verifyURL = siteURL + "/verify?code=" + user.getConfirmationResetToken();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }
}
