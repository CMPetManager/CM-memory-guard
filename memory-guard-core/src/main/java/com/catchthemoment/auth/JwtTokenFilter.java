package com.catchthemoment.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenManager jwtTokenManager;


    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        String bearerToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.replace("Bearer ", "");
        }

        if (bearerToken != null && jwtTokenManager.validateToken(bearerToken)) {

            Authentication authentication = jwtTokenManager.getAuthentication(bearerToken);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}


