package com.catchthemoment.metrics.actuator;

import com.catchthemoment.entity.User;
import com.catchthemoment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Custom actuator  endpoints for
 * finding users
 */
@Component
@Endpoint(id = "user")
@RequiredArgsConstructor
public class UserEndPointActuator {

    private final UserRepository repository;

    @ReadOperation
    public Iterable<User> findAllUsers(){
      return   repository.findAll()
                .stream()
                .filter(user -> !user.getEmail().isEmpty()&& !user.getName().isEmpty())
                .collect(Collectors.toSet());
    }
    @ReadOperation
    public Object selectCustomer(@Selector Long userId) {
        Iterable<User> userList = repository.findAll();
        for (User user : userList) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return String.format("No avaliable user with  id %d", userId);
    }

}
