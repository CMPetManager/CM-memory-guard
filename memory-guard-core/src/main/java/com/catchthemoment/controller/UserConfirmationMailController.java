package com.catchthemoment.controller;

import com.catchthemoment.dto.UserDTO;
import com.catchthemoment.exception.VerifyAccountException;
import com.catchthemoment.service.UserConfirmMailService;
import com.catchthemoment.util.SiteUrlUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserConfirmationMailController {

    private final UserConfirmMailService userConfirmMailService;

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> confirmUserAccount(@RequestBody @NotNull UserDTO requestUser, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        userConfirmMailService.register(requestUser, SiteUrlUtil.getSiteURL(request));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@Param("code") String code) throws VerifyAccountException {
        userConfirmMailService.verifyAccount(code);
        return ResponseEntity.ok("Account verified!");
    }


}

