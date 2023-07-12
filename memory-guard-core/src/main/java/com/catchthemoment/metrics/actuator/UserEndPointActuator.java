package com.catchthemoment.metrics.actuator;

import com.catchthemoment.entity.User;
import com.catchthemoment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.*;

/**
 * Custom actuator  endpoints for
 * finding users
 */
@Component
@Endpoint(id = "user")
@RequiredArgsConstructor
public class UserEndPointActuator {

    private final UserRepository repository;

    /**
     * This method get all users , filters items by email and populate user's map
     * @return map of user filtering by user's email
     */
    @ReadOperation
    public Map<String, Set<User>> findAllUsersByName() {
        return repository.findAll()
                .stream()
                .filter(user -> !user.getEmail().isEmpty() && !user.getName().isEmpty())
                .collect(groupingBy(User::getName
                        , filtering(user -> !user.getEmail().isEmpty(), toSet())));
    }

    @ReadOperation
    public Object selectCustomer(@Selector Long userId) {
        Optional<User> optionalUser = repository.findUserById(userId);
        return optionalUser.isPresent() ? optionalUser.get() : String.format("No avaliable user with  id %d", userId);
    }
}




