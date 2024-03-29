package com.catchthemoment.util;

import com.catchthemoment.entity.User;
import com.catchthemoment.model.LoginRequest;
import com.catchthemoment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * This logic checks that user's has to be log in
 * and his status in db should be enabled.
 * If not user can not request endpoints except of login and registration
 */
@Component
public class LoginHandler {
    @Autowired
    private UserRepository repository;

    public boolean canLogin(LoginRequest loginRequest) {
        return !loginRequest.getEmail().isEmpty();
    }
    public boolean canAccessToAlbumActions(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        var user = this.repository.findUserByEmail(userName);
        if (user.isPresent()){
            User user1 = user.get();
            return user1.isEnabled();
        }
        return false;
    }
}
