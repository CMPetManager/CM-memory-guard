package com.catchthemoment.validation;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.CreateReadUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserApiValidator {
    private static final String REGEX_EMAIL = "([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)";
    private static final String REGEX_NAME = "[a-zA-Z0-9_.-]";

    public boolean isValid(CreateReadUser createReadUser) throws ServiceProcessingException {
        return checkName(createReadUser) &&
                checkEmail(createReadUser) &&
                checkPassword(createReadUser);
    }

    private boolean checkPassword(CreateReadUser createReadUser) {
        log.info("");
        if (createReadUser.getPassword() == null || createReadUser.getPassword().isEmpty()) {
            log.error("Password is null");
            return false;
        } else if (createReadUser.getPassword().length() < 6 || createReadUser.getPassword().length() > 64) {
            log.error("Password length is not correct");
            return false;
        } else if (createReadUser.getPassword().equals(createReadUser.getEmail()) &&
                createReadUser.getPassword().equals(createReadUser.getName())) {
            log.error("Password length shouldn't be equal name or email");
            return false;
        } else return true;
    }

    private boolean checkEmail(CreateReadUser createReadUser) throws ServiceProcessingException {
        if (createReadUser.getEmail() == null || createReadUser.getEmail().isEmpty()) {
            log.error("Email is null");
            return false;
        } else if (createReadUser.getEmail().length() < 5 || createReadUser.getEmail().length() > 255) {
            log.error("Email length is not correct");
            return false;
        } else if (!createReadUser.getEmail().matches(REGEX_EMAIL)) {
            log.error("Incorrect input email");
            return false;
        } else return true;
    }

    private boolean checkName(CreateReadUser createReadUser) {
        if (createReadUser.getName() == null || createReadUser.getName().isEmpty()) {
            log.error("Name shouldn't be null");
            return false;
        } else if (createReadUser.getName().length() < 1 || createReadUser.getName().length() > 64) {
            log.error("Name length is not correct");
            return false;
        } else if (createReadUser.getName().matches(REGEX_NAME)) {
            log.error("Incorrect input name");
            return false;
        } else return true;
    }

}
