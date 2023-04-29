package com.catchthemoment.service;

import com.catchthemoment.auth.JwtEntityFactory;
import com.catchthemoment.entity.Role;
import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

import static com.catchthemoment.exception.ApplicationErrorEnum.ILLEGAL_STATE;
import static com.catchthemoment.exception.ApplicationErrorEnum.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConfirmMailService confirmMailService;

    public User getByEmail(String email) throws ServiceProcessingException {
        log.info("*** Request to get a user by email ***");
        User currentUser = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ServiceProcessingException(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage()));
        log.info("*** User successfully found by email ***");
        return currentUser;
    }

    public User getById(Long userId) throws ServiceProcessingException {
        log.info("*** Request to get a user by ID ***");
        User currentUser = userRepository.findUserById(userId)
                .orElseThrow(() -> new ServiceProcessingException(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage()));
        log.info("*** User successfully found by ID ***");
        return currentUser;
    }

    @Transactional
    public User create(User user, String siteURL) throws ServiceProcessingException,
            UnsupportedEncodingException, MessagingException {
        log.info("*** Checking for mail uniqueness ***");
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            log.info("*** This user is already exists ***");
            throw new ServiceProcessingException(ILLEGAL_STATE.getCode(), ILLEGAL_STATE.getMessage());
        }
        log.info("*** The check was successful ***");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROlE_USER);
        String randomCode = RandomString.make(20);
        user.setConfirmationResetToken(randomCode);
        User createdUser = userRepository.save(user);
        confirmMailService.sendVerificationEmail(user, siteURL);
        log.info("*** The user has been successfully added to the database ***");
        return createdUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User currentUser;
        try {
            currentUser = getByEmail(email);
        } catch (ServiceProcessingException cause) {
            throw new UsernameNotFoundException(cause.getMessage());
        }
        return JwtEntityFactory.create(currentUser);
    }

    @Transactional
    public void deleteUserById(Long userId) throws ServiceProcessingException {
        log.info("*** Request to delete a user by ID ***");
        User currentUser = userRepository.findUserById(userId)
                .orElseThrow(() -> new ServiceProcessingException(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage()));
        log.info("*** User successfully found by ID ***");
        userRepository.deleteById(currentUser.getId());
        log.info("*** User successfully deleted ***");
    }

}

