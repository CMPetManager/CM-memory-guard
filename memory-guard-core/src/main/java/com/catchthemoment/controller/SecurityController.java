package com.catchthemoment.controller;

import com.catchthemoment.model.*;
import com.catchthemoment.service.AuthService;
import com.catchthemoment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class SecurityController implements SecurityControllerApiDelegate {

    private final AuthService authService;

    @Override
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshToken refreshToken) {
        return new ResponseEntity<>(authService.refresh(refreshToken), HttpStatus.OK);
    }
}
