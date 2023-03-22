package com.catchthemoment.controller.security;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.LoginResponse;
import com.catchthemoment.model.Role;
import com.catchthemoment.model.Token;
import com.catchthemoment.model.User;
import com.catchthemoment.service.UserService;
import com.catchthemoment.service.props.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service //fixme -> @Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
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

    public LoginResponse refreshUserTokens(String refreshToken, Long userId) {
        LoginResponse loginResponse = new LoginResponse();

        if (!validateToken(refreshToken)) {
            throw new ServiceProcessingException();
        }
        User user = userService.getById(userId);
        Token token = createTokenForResponse(userId, user);

        loginResponse.setUserId(userId);
        loginResponse.setToken(token);

        return loginResponse;
    }

//    fixme make private
    public Token createTokenForResponse(Long userId, User user) {
        Token token = new Token();
        token.setAccessToken(createAccessToken(userId, user.getEmail(), user.getRole()));
        token.setRefreshToken(createRefreshToken(userId, user.getEmail()));
        token.setExpirationIn(jwtProperties.getAccess());

        return token;
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return !claims.getBody().getExpiration().before(new Date());
    }

    // fixme delete
    private String getId(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id")
                .toString();
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

    public Authentication getAuthentication(String token) {
        String email = getEmail(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}








