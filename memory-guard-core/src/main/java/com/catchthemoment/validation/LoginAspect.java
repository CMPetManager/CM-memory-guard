package com.catchthemoment.validation;

import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.repository.UserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.catchthemoment.exception.ApplicationErrorEnum.INVALID_ACCOUNT;

@Aspect
@Component
public class LoginAspect {

    @Autowired
    private UserRepository repository;

    @Around("@annotation(LoginSuccess)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<User> userOptional = repository.findUsersByName(userName);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            if (user.isEnabled()) {
                return joinPoint.proceed();
            }
        }
        throw new ServiceProcessingException(INVALID_ACCOUNT);
    }
}
