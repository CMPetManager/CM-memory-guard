package com.catchthemoment.controller;

import com.catchthemoment.model.LoginRequest;
import com.catchthemoment.model.LoginResponse;
import com.catchthemoment.model.RefreshToken;
import com.catchthemoment.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityController implements SecurityControllerApiDelegate {

	private final AuthService authService;

	@Override
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authService.login(loginRequest));
	}

	@Override
	public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshToken refreshToken) {
		return new ResponseEntity<>(authService.refresh(refreshToken), HttpStatus.OK);
	}
}
