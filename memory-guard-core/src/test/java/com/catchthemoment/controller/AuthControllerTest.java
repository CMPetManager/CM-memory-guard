package com.catchthemoment.controller;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.LoginRequest;
import com.catchthemoment.model.LoginResponse;
import com.catchthemoment.model.RefreshToken;
import com.catchthemoment.model.Token;
import com.catchthemoment.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void testLogin() throws ServiceProcessingException {
        LoginRequest loginRequest = getLoginRequest();
        LoginResponse loginResponse = getLoginResponse();
        doReturn(loginResponse).when(authService).login(loginRequest);

        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginResponse, response.getBody());
        verify(authService, times(1)).login(loginRequest);
    }

    @Test
    void testRefresh() throws ServiceProcessingException {
        RefreshToken refreshToken = getRefreshToken();
        LoginResponse loginResponse = getLoginResponse();
        doReturn(loginResponse).when(authService).refresh(refreshToken);

        ResponseEntity<LoginResponse> response = authController.refresh(refreshToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginResponse, response.getBody());
        verify(authService, times(1)).refresh(refreshToken);
    }

    private static RefreshToken getRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken("refresh-token");
        return refreshToken;
    }

    private Token getToken() {
        Token token = new Token();
        token.setRefreshToken("refresh-token");
        token.setAccessToken("access-token");
        return token;
    }

    private LoginResponse getLoginResponse() {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(getToken());
        return loginResponse;
    }

    private LoginRequest getLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@example.com");
        loginRequest.setPassword("password");
        return loginRequest;
    }
}