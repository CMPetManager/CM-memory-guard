package com.catchthemoment.repository;

import com.catchthemoment.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//Repository annotation is not useful , spring confess that it will be a repo
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(value = "usr-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> findUserById(Long userId);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUSerByConfirmationResetToken(String code);

    @Transactional(readOnly = true)
    Optional<User> findUserByResetPasswordToken(String token);
}
