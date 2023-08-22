package com.catchthemoment.auth;

import com.catchthemoment.exception.ServiceProcessingException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.catchthemoment.exception.ApplicationErrorEnum.TOKEN_TIME_EXPIRED;

@AllArgsConstructor
@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenManager jwtTokenManager;

    private final JwtUtils utils;


    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {


        int localTime = LocalDateTime.now().getSecond();
        if (localTime > utils.getJwtExpirationTime()) {
            throw new ServiceProcessingException(TOKEN_TIME_EXPIRED);
        }
        String bearerToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.replace("Bearer ", "");
        }

        if (bearerToken != null && jwtTokenManager.validateToken(bearerToken)) {

            Authentication authentication = jwtTokenManager.getAuthentication(bearerToken);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            log.warn("**authentication must be fall**", authentication);

        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

}




