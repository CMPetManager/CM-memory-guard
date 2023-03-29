package com.catchthemoment.service;

import com.catchthemoment.auth.JwtEntityFactory;
import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ApplicationErrorEnum;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


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

}
