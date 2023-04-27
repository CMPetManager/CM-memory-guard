package com.catchthemoment.controller;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.CreateReadUser;
import com.catchthemoment.service.UserEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserEmailController implements UserControllerApiDelegate {

    private final UserEmailService service;

    public ResponseEntity<Void> updateExistsEmail(@PathVariable Long usrId,
                                                  @RequestBody @Validated @ModelAttribute CreateReadUser createReadUser)
            throws ServiceProcessingException {
        log.info("***change user' email from request create user model", createReadUser);
        service.changeUserEmail(usrId, createReadUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
