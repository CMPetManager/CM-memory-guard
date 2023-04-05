package com.catchthemoment.controller;

import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ApplicationErrorEnum;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.exception.VerifyAccountException;
import com.catchthemoment.mappers.CreateReadUserMapper;
import com.catchthemoment.model.CreateReadUser;
import com.catchthemoment.service.UserConfirmMailService;
import com.catchthemoment.service.UserService;
import com.catchthemoment.util.SiteUrlUtil;
import com.catchthemoment.validation.UserApiValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserConfirmationMailController {

    private final UserConfirmMailService userConfirmMailService;
    private final UserService userService;
    private final CreateReadUserMapper userMapper;
    private final UserApiValidator validator;

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateReadUser> confirmUserAccount(@RequestBody @NotNull CreateReadUser createReadUser, HttpServletRequest request)
            throws Exception {
        log.info("Received a registration request by email: {}", createReadUser.getEmail());
        if(!validator.isValid(createReadUser))
            throw new ServiceProcessingException(ApplicationErrorEnum.INCORRECT_INPUT.getCode(),
                    ApplicationErrorEnum.INCORRECT_INPUT.getMessage());
        User user = userMapper.toEntity(createReadUser);
        CreateReadUser createdUser = userMapper.toDto(userService.create(user, SiteUrlUtil.getSiteURL(request)));
        ResponseEntity<CreateReadUser> response = new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        log.info("The user has been successfully registered");
        return response;
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@Param("code") String code) throws VerifyAccountException {
        userConfirmMailService.verifyAccount(code);
        return ResponseEntity.ok("Account verified!");
    }


}

