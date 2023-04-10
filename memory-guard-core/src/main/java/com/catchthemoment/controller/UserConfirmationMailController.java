package com.catchthemoment.controller;

import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.mappers.UserApiMapper;
import com.catchthemoment.model.UserAPI;
import com.catchthemoment.service.UserConfirmMailService;
import com.catchthemoment.service.UserService;
import com.catchthemoment.util.SiteUrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserConfirmationMailController {

    private final UserConfirmMailService userConfirmMailService;
    private final UserService userService;
    private final UserApiMapper userApiMapper;

    @GetMapping(value = "/confirm-account",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAPI> confirmUserAccount(@RequestBody @NotNull UserAPI userAPI, HttpServletRequest request)
            throws Exception {
        log.info("Received a registration request by email: {}", userAPI.getEmail());
        User user = userApiMapper.fromUserApi(userAPI);
        UserAPI createdUser = userApiMapper.fromUserEntity(userService.create(user, SiteUrlUtil.getSiteURL(request)));
        ResponseEntity<UserAPI> response = new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        log.info("The user has been successfully registered");
        return response;
    }

    @GetMapping("/verify")
    public ResponseEntity<Object> verifyAccount(@Param("code") String code, Model model) throws ServiceProcessingException {
        userConfirmMailService.verifyAccount(code);
        return ResponseEntity.ok().body("Account has been verified!");
    }


}

