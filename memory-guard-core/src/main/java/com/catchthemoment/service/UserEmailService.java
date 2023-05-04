package com.catchthemoment.service;

import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.CreateReadUser;
import com.catchthemoment.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

import static com.catchthemoment.exception.ApplicationErrorEnum.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserEmailService {

    private final UserRepository repository;
    private final UserConfirmMailService userConfirmMailService;

    public void changeUserEmail(Long userId, CreateReadUser readUser, String sitUrl) throws ServiceProcessingException,
            MessagingException, UnsupportedEncodingException {
        if (readUser.getEmail().isEmpty()) {
            log.error("*** user's email is  not found or empty ***");
            throw new ServiceProcessingException(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage());
        }
        var user = repository.findUserById(userId).orElse(new User());
        user.setEmail(readUser.getEmail());

        String randomCode = RandomString.make(20);
        user.setConfirmationResetToken(randomCode);
        repository.save(user);

        userConfirmMailService.sendVerificationEmail(user, sitUrl);
    }
}
