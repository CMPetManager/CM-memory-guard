package com.catchthemoment.service;

import com.catchthemoment.auth.JwtEntityFactory;
import com.catchthemoment.exception.ApplicationErrorEnum;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.User;
import com.catchthemoment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public User getByEmail(String email) throws ServiceProcessingException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ServiceProcessingException(ApplicationErrorEnum.USER_NOT_FOUND.getCode(),
                        ApplicationErrorEnum.USER_NOT_FOUND.getMessage()));
    }

    public User getById(Long userId) throws ServiceProcessingException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ServiceProcessingException(ApplicationErrorEnum.USER_NOT_FOUND.getCode(),
                        ApplicationErrorEnum.USER_NOT_FOUND.getMessage()));
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
