package com.catchthemoment.auth;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Getter
public class JwtUtils {

    @Value("${security.jwt.access}")
    private int jwtExpirationTime;

    @Value("${security.jwt.secret}")
    private String jwtSecret;

}
