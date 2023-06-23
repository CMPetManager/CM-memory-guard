package com.catchthemoment.controller;

import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.mappers.UserModelMapper;
import com.catchthemoment.model.UserModel;
import com.catchthemoment.service.UserConfirmMailService;
import com.catchthemoment.service.UserService;
import com.catchthemoment.util.SiteUrlUtil;
import com.catchthemoment.validation.UserModelValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.validation.constraints.NotNull;

import static com.catchthemoment.exception.ApplicationErrorEnum.INCORRECT_INPUT;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserConfirmationMailController implements UserConfirmationMailControllerApiDelegate {

    private final UserConfirmMailService userConfirmMailService;
    private final UserService userService;
    private final UserModelMapper userMapper;
    private final UserModelValidator validator;

    @Override
    public ResponseEntity<UserModel> confirmUserAccount(@RequestBody @NotNull UserModel userModel)
            throws Exception {
        log.info("*** Received a registration request by email: {} ***", userModel.getEmail());
        if (!validator.isValid(userModel)) {
            log.error("*** CreateReadUser didn't pass validation ***");
            throw new ServiceProcessingException(INCORRECT_INPUT);
        }
        User user = userMapper.toEntity(userModel);
        User currentUser = userService.create(user, SiteUrlUtil.getSiteURL(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()));
        UserModel createdUser = userMapper.toDto(currentUser);
        ResponseEntity<UserModel> response = new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        log.info("*** The user has been successfully registered ***");
        return response;
    }

    @Override
    public ResponseEntity<String> verifyAccount(@RequestPart String token) throws ServiceProcessingException {
        userConfirmMailService.verifyAccount(token);
        return ResponseEntity.ok().body("Account has been verified!!");

    }
}
