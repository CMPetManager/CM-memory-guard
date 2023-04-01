package com.catchthemoment.service;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.auth.JwtTokenManager;
import com.catchthemoment.config.JwtProperties;
import com.catchthemoment.model.LoginRequest;
import com.catchthemoment.model.LoginResponse;

import com.catchthemoment.entity.User;
import com.catchthemoment.model.RefreshToken;
import com.catchthemoment.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;
    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;

    public LoginResponse login(LoginRequest loginRequest) throws ServiceProcessingException {

        User currentUser = userService.getByEmail(loginRequest.getEmail());

        log.info("The beginning of authentication");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        log.info("Authentication successfully");

        log.info("Create object for response");
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(currentUser.getId());
        loginResponse.setName(currentUser.getName());
        loginResponse.setToken(getToken(currentUser));
        log.info("LoginResponse successfully created");
        return loginResponse;
    }

    public LoginResponse refresh(RefreshToken refreshToken) throws ServiceProcessingException {
        return jwtTokenManager.refreshUserTokens(refreshToken.getRefreshToken(), refreshToken.getUserId());
    }

    private Token getToken(User user) {
        Token token = new Token();
        token.setAccessToken(jwtTokenManager.createAccessToken(user.getId(),
                user.getEmail(),
                user.getRole()));
        token.setRefreshToken(jwtTokenManager.createRefreshToken(user.getId(),
                user.getEmail()));
        token.setExpirationIn(jwtProperties.getAccess());
        return token;
    }
}
