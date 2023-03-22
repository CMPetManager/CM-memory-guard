package com.catchthemoment.service;

import com.catchthemoment.model.*;
import com.catchthemoment.controller.security.JwtTokenProvider;
import com.catchthemoment.service.props.JwtProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith({MockitoExtension.class})
class AuthServiceTest {
    private static final Long USER_ID = 1L;
    @Mock
    private UserService userService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtProperties jwtProperties;
    @InjectMocks
    private AuthService authService;

    @Test
    void login() {
        LoginResponse expectedResult = getLoginResponse();
        LoginRequest loginRequest = getLoginRequest();
        User user = getUser();
        doReturn(user).when(userService).getByEmail(loginRequest.getEmail());
        doReturn(expectedResult.getToken().getAccessToken()).when(jwtTokenProvider)
                .createAccessToken(user.getId(), user.getEmail(), user.getRole());
        doReturn(expectedResult.getToken().getRefreshToken()).when(jwtTokenProvider)
                .createRefreshToken(user.getId(), user.getEmail());
        doReturn(expectedResult.getToken().getExpirationIn()).when(jwtProperties).getAccess();

        LoginResponse actualResult = authService.login(loginRequest);

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void refresh() {
        LoginResponse expectedResult = getLoginResponse();
        RefreshToken refreshToken = getRefreshToken();
        doReturn(expectedResult).when(jwtTokenProvider)
                .refreshUserTokens(refreshToken.getRefreshToken(), refreshToken.getUserId());

        LoginResponse actualResult = authService.refresh(refreshToken);

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void refreshFailed() {
        RefreshToken refreshToken = getRefreshToken();
        doReturn(null).when(jwtTokenProvider).refreshUserTokens(any(), any());

        LoginResponse actualResult = authService.refresh(refreshToken);

        assertThat(actualResult).isNull();
    }

    private LoginResponse getLoginResponse() {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(USER_ID);
        loginResponse.setToken(getToken());
        return loginResponse;
    }

    private LoginRequest getLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("hello@gmail.com");
        loginRequest.setPassword("111");

        return loginRequest;
    }

    private Token getToken() {
        Token token = new Token();
        token.setAccessToken("tokenAccess");
        token.setRefreshToken("tokenRefresh");
        token.setExpirationIn(3600000L);

        return token;
    }

    private RefreshToken getRefreshToken() {
        RefreshToken token = new RefreshToken();
        token.setUserId(USER_ID);
        token.setRefreshToken("tokenRefresh");

        return token;
    }

    private User getUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setName("Ivan");
        user.setEmail("hello@gmail.com");
        user.setPassword("111");
        user.setRole(Role.ROLE_USER);

        return user;
    }
}