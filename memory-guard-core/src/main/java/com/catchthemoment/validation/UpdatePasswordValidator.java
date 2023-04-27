package com.catchthemoment.validation;

import com.catchthemoment.model.UpdatePassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class UpdatePasswordValidator {

    public boolean isValid(UpdatePassword updatePassword){
        if (!StringUtils.hasText(updatePassword.getPassword())) {
            log.error("*** Password is null ***");
            return false;
        } else if (updatePassword.getPassword().length() < 6 || updatePassword.getPassword().length() > 25) {
            log.error("*** Password length is not correct ***");
            return false;
        }
        log.info("*** Password passed validation ***");
        return true;
    }
}
