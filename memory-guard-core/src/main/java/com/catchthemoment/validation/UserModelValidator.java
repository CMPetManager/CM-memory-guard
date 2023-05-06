package com.catchthemoment.validation;

import com.catchthemoment.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class UserModelValidator {
    private static final String REGEX_EMAIL = "([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)";
    private static final String REGEX_NAME = "[a-zA-Z0-9_.-]";

    public boolean isValid(UserModel userModel) {
        return checkName(userModel.getName())
                && checkEmail(userModel.getEmail())
                && checkPassword(userModel);
    }

    private boolean checkPassword(UserModel userModel) {
        if (!StringUtils.hasText(userModel.getPassword())) {
            log.error("*** Password is null ***");
            return false;
        } else if (userModel.getPassword().length() < 6 || userModel.getPassword().length() > 64) {
            log.error("*** Password length is not correct ***");
            return false;
        } else if (userModel.getPassword().equals(userModel.getEmail()) &&
                userModel.getPassword().equals(userModel.getName())) {
            log.error("*** Password length shouldn't be equal name or email ***");
            return false;
        } else return true;
    }

    private boolean checkEmail(String email) {
        if (!StringUtils.hasText(email)) {
            log.error("*** Email is null ***");
            return false;
        } else if (email.length() < 5 || email.length() > 255) {
            log.error("*** Email length is not correct ***");
            return false;
        } else if (!email.matches(REGEX_EMAIL)) {
            log.error("*** Incorrect input email ***");
            return false;
        } else return true;
    }

    private boolean checkName(String name) {
        if (!StringUtils.hasText(name)) {
            log.error("*** Name shouldn't be null ***");
            return false;
        } else if (name.length() < 1 || name.length() > 64) {
            log.error("*** Name length is not correct ***");
            return false;
        } else if (name.matches(REGEX_NAME)) {
            log.error("*** Incorrect input name ***");
            return false;
        } else return true;
    }

}
