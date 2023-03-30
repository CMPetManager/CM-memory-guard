package com.catchthemoment.validation;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.UserAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserApiValidator {
    private static final String REGEX_EMAIL = "([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)";
    private static final String REGEX_NAME = "[a-zA-Z0-9_.-]";

    public boolean isValid(UserAPI userAPI) throws ServiceProcessingException {
        return checkName(userAPI) &&
                checkEmail(userAPI) &&
                checkPassword(userAPI);
    }

    private boolean checkPassword(UserAPI userAPI) {
        log.info("");
        if (userAPI.getPassword() == null || userAPI.getPassword().isEmpty()) {
            log.error("Password is null");
            return false;
        } else if (userAPI.getPassword().length() < 6 || userAPI.getPassword().length() > 64) {
            log.error("Password length is not correct");
            return false;
        } else if (userAPI.getPassword().equals(userAPI.getEmail()) &&
                userAPI.getPassword().equals(userAPI.getName())) {
            log.error("Password length shouldn't be equal name or email");
            return false;
        } else return true;
    }

    private boolean checkEmail(UserAPI userAPI) throws ServiceProcessingException {
        if (userAPI.getEmail() == null || userAPI.getEmail().isEmpty()) {
            log.error("Email is null");
            return false;
        } else if (userAPI.getEmail().length() < 5 || userAPI.getEmail().length() > 255) {
            log.error("Email length is not correct");
            return false;
        } else if (!userAPI.getEmail().matches(REGEX_EMAIL)) {
            log.error("Incorrect input email");
            return false;
        } else return true;
    }

    private boolean checkName(UserAPI userAPI) {
        if (userAPI.getName() == null || userAPI.getName().isEmpty()) {
            log.error("Name shouldn't be null");
            return false;
        } else if (userAPI.getName().length() < 1 || userAPI.getName().length() > 64) {
            log.error("Name length is not correct");
            return false;
        } else if (userAPI.getName().matches(REGEX_NAME)) {
            log.error("Incorrect input name");
            return false;
        } else return true;
    }

}
