package com.catchthemoment.service;

import com.catchthemoment.model.*;
import com.catchthemoment.controller.security.JwtTokenProvider;
import com.catchthemoment.service.props.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;

    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        User foundUser = userService.getByEmail(loginRequest.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        Token token = new Token();
        token.setAccessToken(jwtTokenProvider.createAccessToken(foundUser.getId(),
                foundUser.getEmail(),
                foundUser.getRole()));
        token.setRefreshToken(jwtTokenProvider.createRefreshToken(foundUser.getId(),
                foundUser.getEmail()));
        token.setExpirationIn(jwtProperties.getAccess());

        loginResponse.setUserId(foundUser.getId());
        loginResponse.setToken(token);

        return loginResponse;
    }

    public LoginResponse refresh(RefreshToken refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken.getRefreshToken(), refreshToken.getUserId());
    }
}
