package com.catchthemoment.controller;

import com.catchthemoment.model.ChangeEmailRequestModel;
import com.catchthemoment.service.UserEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class UserChangeEmailController implements UserChangeEmailControllerApiDelegate {

    private final UserEmailService userEmailService;

    @Override
    public ResponseEntity<Void> changeEmail(Long usrId, ChangeEmailRequestModel changeEmailRequestModel)
            throws Exception {
        log.info("*** change user's email from request create user model: {}", changeEmailRequestModel.getNewEmail());
        userEmailService.changeUserEmail(usrId, changeEmailRequestModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
