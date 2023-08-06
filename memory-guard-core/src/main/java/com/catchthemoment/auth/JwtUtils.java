package com.catchthemoment.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.catchthemoment.exception.ServiceProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.catchthemoment.exception.ApplicationErrorEnum.TOKEN_TIME_EXPIRED;

@Component
@Slf4j
public class JwtUtils {

    @Value("${security.jwt.access}")
    private int jwtExpirationTime;

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    public boolean isJwtExpiredTimeAt(String token) throws ServiceProcessingException {
        token = jwtSecret;
        DecodedJWT decodedJWT = JWT.decode(token);
        var time = decodedJWT.getExpiresAtAsInstant();
        if (time.getNano() > jwtExpirationTime) {
            throw new ServiceProcessingException(TOKEN_TIME_EXPIRED);

        }
        return true;

    }

}
