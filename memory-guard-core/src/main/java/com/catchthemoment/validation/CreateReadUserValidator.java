package com.catchthemoment.validation;

import com.catchthemoment.model.CreateReadUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class CreateReadUserValidator {
    private static final String REGEX_EMAIL = "([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)";
    private static final String REGEX_NAME = "[a-zA-Z0-9_.-]";

    public boolean isValid(CreateReadUser createReadUser) {
        return checkName(createReadUser.getName())
                && checkEmail(createReadUser.getEmail())
                && checkPassword(createReadUser);
    }

    private boolean checkPassword(CreateReadUser createReadUser) {
        log.info("");
        if (!StringUtils.hasText(createReadUser.getPassword())) {
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

    private boolean checkEmail(String email) {
        if (!StringUtils.hasText(email)) {
            log.error("Email is null");
            return false;
        } else if (email.length() < 5 || email.length() > 255) {
            log.error("Email length is not correct");
            return false;
        } else if (!email.matches(REGEX_EMAIL)) {
            log.error("Incorrect input email");
            return false;
        } else return true;
    }

    private boolean checkName(String name) {
        if (!StringUtils.hasText(name)) {
            log.error("Name shouldn't be null");
            return false;
        } else if (name.length() < 1 || name.length() > 64) {
            log.error("Name length is not correct");
            return false;
        } else if (name.matches(REGEX_NAME)) {
            log.error("Incorrect input name");
            return false;
        } else return true;
    }

}
