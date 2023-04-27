package com.catchthemoment.service;

import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.mappers.CreateReadUserMapper;
import com.catchthemoment.model.CreateReadUser;
import com.catchthemoment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.catchthemoment.exception.ApplicationErrorEnum.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserEmailService {

    private final UserRepository repository;
    private CreateReadUserMapper mapper;

    public void changeUserEmail(Long userId, CreateReadUser readUser) throws ServiceProcessingException {
        if (readUser.getEmail().isEmpty() || readUser == null) {
            log.error("*** user not found or empty***");
            throw new ServiceProcessingException(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage());
        }
        var usr = repository.findUserById(userId).orElse(new User());
        usr.setEmail(readUser.getEmail());
        repository.save(usr);
    }
}
