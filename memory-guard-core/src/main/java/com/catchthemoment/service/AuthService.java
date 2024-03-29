package com.catchthemoment.service;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.auth.JwtTokenManager;
import com.catchthemoment.model.LoginRequest;
import com.catchthemoment.model.LoginResponse;

import com.catchthemoment.entity.User;
import com.catchthemoment.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) throws ServiceProcessingException {
        User currentUser = userService.getByEmail(loginRequest.getEmail());
        log.info("*** The beginning of authentication ***");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        log.info("*** Authentication passed successfully ***");
        return getLoginResponse(currentUser);
    }

    public LoginResponse refresh(RefreshToken refreshToken) throws ServiceProcessingException {
        return jwtTokenManager.refreshUserTokens(refreshToken.getRefreshToken(), refreshToken.getUserId());
    }

    private LoginResponse getLoginResponse(User currentUser) {
        log.info("*** Create object for response ***");
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(currentUser.getId());
        loginResponse.setName(currentUser.getName());
        loginResponse.setToken(jwtTokenManager.getToken(currentUser));
        log.info("*** LoginResponse successfully created ***");
        return loginResponse;
    }
}
