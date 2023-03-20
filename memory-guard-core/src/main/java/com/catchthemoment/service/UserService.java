package com.catchthemoment.service;

import com.catchthemoment.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    public Optional<User> getByUsername(String email) {
        return null;
    }

    public User getById(Long userId) {
        return null;
    }




}
