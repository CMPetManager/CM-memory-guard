package com.catchthemoment.service;

import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.ForgotPassword;
import com.catchthemoment.model.UpdatePasswordModel;
import com.catchthemoment.repository.UserRepository;
import com.catchthemoment.validation.LoginSuccess;
import com.catchthemoment.validation.Password;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.catchthemoment.exception.ApplicationErrorEnum.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserResetPasswordService {

    private final UserRepository repository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailAddress;
    @Value("${spring.application.name}")
    private String sender;

    @Transactional(rollbackFor = Exception.class)
    public void updateResetPasswordToken(@NotNull String email, String token) throws ServiceProcessingException {
        var user = repository.findUserByEmail(email).orElseThrow(
                () -> new ServiceProcessingException(MAIL_INCORRECT));
        user.setResetPasswordToken(token);
        repository.save(user);
    }

    @LoginSuccess
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.REPEATABLE_READ,rollbackFor = Exception.class)
    public void changeUserPasswords(@NotNull @Valid UpdatePasswordModel passwordModel) throws ServiceProcessingException {
        var newPassword = passwordModel.getNewPassword();
        var user = repository.findUserByPassword(passwordModel.getOldPassword());
        if (user.isPresent()) {
            User newUser = user.get();
            newUser.setPassword(newPassword);
            repository.save(newUser);
        } else {
            throw new ServiceProcessingException(PASSWORD_INPUT_FAILS);
        }
    }

    public User getUserFromResetToken(String password) throws ServiceProcessingException {
        return repository.findUserByPassword(password).
                orElseThrow(() -> new ServiceProcessingException(USER_NOT_FOUND));
    }

    @Transactional(rollbackFor = Exception.class)
    public void forgotPassword(@NotNull ForgotPassword forgotPassword) throws ServiceProcessingException {
        var userOptional = repository.findUserByEmail(forgotPassword.getEmail());
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();
            currentUser.setPassword(forgotPassword.getNewPassword());
            repository.save(currentUser);
            log.info("**new password updated well");
        } else {
            throw new ServiceProcessingException(PASSWORD_INPUT_FAILS);
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @LoginSuccess
    public void updatePassword(@NotNull User reqUser, @Password String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        reqUser.setPassword(encodedPassword);
        reqUser.setResetPasswordToken(null);
        repository.save(reqUser);
    }

    public void sendResetPasswordEmail(String mailRecipient, String link) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(mailAddress);
        helper.setTo(mailRecipient);

        String subject = "Here's the link to reset your password";
        String content =
                " <p>Hello,</p>" +
                        "<p>You have requested to reset your password.</p>" +
                        " <p>Click the link below to change your password:</p>" +
                        "<p><a href=\"" + link + ">Change my password</a></p>" +
                        " <br>" +
                        " <p>Ignore this email if you do remember your password," +
                        "or you have not made the request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(message);
    }
}
