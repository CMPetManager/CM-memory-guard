package com.catchthemoment.service;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.*;
import com.catchthemoment.auth.JwtTokenManager;
import com.catchthemoment.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;
    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;

    public LoginResponse login(LoginRequest loginRequest) throws ServiceProcessingException {
        LoginResponse loginResponse = new LoginResponse();
        User currentUser = userService.getByEmail(loginRequest.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        loginResponse.setUserId(currentUser.getId());
        loginResponse.setToken(getToken(currentUser));

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
