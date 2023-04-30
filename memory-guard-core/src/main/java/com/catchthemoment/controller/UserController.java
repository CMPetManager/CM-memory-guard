package com.catchthemoment.controller;

import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ApplicationErrorEnum;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.CreateReadUser;
import com.catchthemoment.model.UpdatePassword;
import com.catchthemoment.service.UserEmailService;
import com.catchthemoment.service.UserResetPasswordService;
import com.catchthemoment.service.UserService;
import com.catchthemoment.util.SiteUrlUtil;
import com.catchthemoment.validation.UpdatePasswordValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.catchthemoment.exception.ApplicationErrorEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserController implements UserControllerApiDelegate {

    private static final String RESPONSE_UPDATE_PASSWORD = "Password successfully updated";
    private static final String RESPONSE_DELETE_USER = "User successfully deleted";

    private final UserResetPasswordService userResetPasswordService;
    private final UserService userService;
    private final UserEmailService userEmailservice;
    private final UpdatePasswordValidator validator;


    @PatchMapping(value = "/users/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEmail(@PathVariable Long userId, @RequestBody @NotNull CreateReadUser createReadUser, HttpServletRequest request) throws Exception {
        log.info("*** change user's email from request create user model: {}", createReadUser.getEmail());
        userEmailservice.changeUserEmail(userId, createReadUser, SiteUrlUtil.getSiteURL(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updatePassword(Long userId, UpdatePassword updatePassword) throws Exception {
        if (!validator.isValid(updatePassword)) {
            log.error("*** Password didn't pass validation ***");
            throw new ServiceProcessingException(INCORRECT_INPUT.getCode(),
                    INCORRECT_INPUT.getMessage());
        }
        User currentUser = userService.getById(userId);
        log.info("*** Received an update password request with email: {} ***", currentUser.getEmail());
        userResetPasswordService.updatePassword(currentUser, updatePassword.getPassword());
        log.info("*** " + RESPONSE_UPDATE_PASSWORD + " ***");

        return ResponseEntity.ok(RESPONSE_UPDATE_PASSWORD);
    }

    @Override
    public ResponseEntity<String> deleteUser(Long userId) throws Exception {
        log.info("*** Received an delete account request ***");
        userService.deleteUserById(userId);
        log.info("*** " + RESPONSE_DELETE_USER + " ***");

        return ResponseEntity.ok(RESPONSE_DELETE_USER);
    }
}
