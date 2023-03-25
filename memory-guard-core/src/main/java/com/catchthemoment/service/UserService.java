package com.catchthemoment.service;

import com.catchthemoment.auth.JwtEntityFactory;
import com.catchthemoment.entity.Role;
import com.catchthemoment.exception.ApplicationErrorEnum;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.entity.User;
import com.catchthemoment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getByEmail(String email) throws ServiceProcessingException {
        log.info("Request to get a user by email");
        User currentUser = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ServiceProcessingException(ApplicationErrorEnum.USER_NOT_FOUND.getCode(),
                        ApplicationErrorEnum.USER_NOT_FOUND.getMessage()));
        log.info("User successfully found");
        return currentUser;
    }

    public User getById(Long userId) throws ServiceProcessingException {
        log.info("Request to get a user by ID");
        User currentUser = userRepository.findUserById(userId)
                .orElseThrow(() -> new ServiceProcessingException(ApplicationErrorEnum.USER_NOT_FOUND.getCode(),
                        ApplicationErrorEnum.USER_NOT_FOUND.getMessage()));
        log.info("User successfully found");
        return currentUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User currentUser;
        try {
            currentUser = getByEmail(email);
        } catch (ServiceProcessingException e) {
            throw new RuntimeException(e);
        }
        return JwtEntityFactory.create(currentUser);
    }

    @Transactional
    public User create(User user) throws ServiceProcessingException {
        log.info("Checking for mail uniqueness");
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ServiceProcessingException(ApplicationErrorEnum.ILLEGAL_STATE.getCode(),
                    ApplicationErrorEnum.ILLEGAL_STATE.getMessage());
        }
        log.info("The check was successful");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROlE_USER);
        User createdUser = userRepository.save(user);
        log.info("The user has been successfully added to the database");
        return createdUser;
    }
}
