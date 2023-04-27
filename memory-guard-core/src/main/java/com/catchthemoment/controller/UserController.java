package com.catchthemoment.controller;

import com.catchthemoment.entity.User;
import com.catchthemoment.model.UpdatePassword;
import com.catchthemoment.service.UserResetPasswordService;
import com.catchthemoment.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserController implements UserControllerApiDelegate {

    private static final String RESPONSE_UPDATE_PASSWORD = "Password successfully updated";

    private final UserResetPasswordService userResetPasswordService;
    private final UserService userService;

    @Override
    public ResponseEntity<String> updateEmail(Long userId, UpdatePassword updatePassword) throws Exception {
        User currentUser = userService.getById(userId);
        log.info("*** Received an update password request with email: {} ***", currentUser.getEmail());
        userResetPasswordService.updatePassword(currentUser, updatePassword.getPassword());
        log.info("*** " + RESPONSE_UPDATE_PASSWORD + " ***");
        return ResponseEntity.ok(RESPONSE_UPDATE_PASSWORD);
    }
}
