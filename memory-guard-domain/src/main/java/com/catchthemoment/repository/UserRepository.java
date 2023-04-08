package com.catchthemoment.repository;

import com.catchthemoment.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(value = "usr-entity-graph", type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "select u from User u where u.id = :id")
    Optional<User> findUserById(@Param("id") Long userId);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUSerByConfirmationResetToken(String code);

    @Transactional(readOnly = true)
    Optional<User> findUserByResetPasswordToken(String token);
}
