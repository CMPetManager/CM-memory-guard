package com.catchthemoment.service;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.UserModel;
import com.catchthemoment.repository.UserRepository;
import com.catchthemoment.validation.LoginSuccess;
import com.catchthemoment.validation.ReadDataTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

import static com.catchthemoment.exception.ApplicationErrorEnum.USER_NOT_FOUND;

@Service
@Slf4j
@ReadDataTransactional
@RequiredArgsConstructor
public class UserEmailService {

    private final UserRepository repository;
    private final UserConfirmMailService userConfirmMailService;
    @Value("${application.url}")
    private String siteUrl;

    @LoginSuccess
    public void changeUserEmail(Long userId, UserModel readUser) throws ServiceProcessingException,
            MessagingException, UnsupportedEncodingException {
        if (readUser.getEmail().isEmpty()) {
            log.error("*** user's email is not found or empty ***");
            throw new ServiceProcessingException(USER_NOT_FOUND);
        }
        var user = repository.findUserById(userId).orElseThrow();
        user.setEmail(readUser.getEmail());

        String randomCode = RandomString.make(20);
        user.setConfirmationResetToken(randomCode);
        user.setEnabled(false);
        repository.save(user);
        userConfirmMailService.sendVerificationEmail(user, siteUrl);
    }
}

