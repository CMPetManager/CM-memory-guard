package com.catchthemoment.auth;

import com.catchthemoment.exception.ApplicationErrorEnum;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.LoginResponse;
import com.catchthemoment.entity.Role;
import com.catchthemoment.model.Token;
import com.catchthemoment.entity.User;
import com.catchthemoment.service.UserService;
import com.catchthemoment.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenManager {

    private final JwtProperties jwtProperties;
    private final UserService userService;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(Long userId, String email, Role role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("id", userId);
        claims.put("role", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(Long userId, String email) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("id", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public LoginResponse refreshUserTokens(String refreshToken, Long userId) throws ServiceProcessingException {

        if (!validateToken(refreshToken)) {
            throw new ServiceProcessingException(ApplicationErrorEnum.ACCESS_DENIED.getCode(),
                    ApplicationErrorEnum.ACCESS_DENIED.getMessage());
        }
        User user = userService.getById(userId);
        Token token = createTokenForResponse(userId, user);

        return getLoginResponse(userId, user, token);

    }

    private static LoginResponse getLoginResponse(Long userId, User user, Token token) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(userId);
        loginResponse.setName(user.getName());
        loginResponse.setToken(token);
        return loginResponse;
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return !claims.getBody().getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token) {
        String email = getEmail(token);
        UserDetails userDetails = userService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Token createTokenForResponse(Long userId, User user) {
        Token token = new Token();
        token.setAccessToken(createAccessToken(userId, user.getEmail(), user.getRole()));
        token.setRefreshToken(createRefreshToken(userId, user.getEmail()));
        token.setExpirationIn(jwtProperties.getAccess());

        return token;
    }

    public Token getToken(User user) {
        Token token = new Token();
        token.setAccessToken(createAccessToken(
                user.getId(),
                user.getEmail(),
                user.getRole()));
        token.setRefreshToken(createRefreshToken(
                user.getId(),
                user.getEmail()));
        token.setExpirationIn(jwtProperties.getAccess());
        return token;
    }

    private String getEmail(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}








