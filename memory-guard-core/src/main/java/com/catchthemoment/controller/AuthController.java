package com.catchthemoment.controller;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.LoginRequest;
import com.catchthemoment.model.LoginResponse;
import com.catchthemoment.model.RefreshToken;
import com.catchthemoment.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController implements AuthControllerApiDelegate {

	private final AuthService authService;

	@Override
	public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) throws ServiceProcessingException {
		log.info("*** Received an authentication request by email: {} ***",loginRequest.getEmail());
		ResponseEntity<LoginResponse> response = ResponseEntity.ok(authService.login(loginRequest));
		log.info("*** Authentication successful ***");
		return response;
	}

	@Override
	public ResponseEntity<LoginResponse> refresh(RefreshToken refreshToken) throws ServiceProcessingException {
		log.info("*** Received a request to refresh a token ***");
		ResponseEntity<LoginResponse> response = ResponseEntity.ok(authService.refresh(refreshToken));
		log.info("*** Tokens have been successfully updated ***");
		return response;
	}

}
