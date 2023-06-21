package com.catchthemoment.repository;

import com.catchthemoment.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findUserById(@Param("id") Long userId);
    
    @Query(value = "select usr from User usr where usr.email =:email")
    @EntityGraph(value = "user-graph", type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"albums"})
    Optional<User> findUserByEmail(@Param("email") String email);

    Optional<User> findUSerByConfirmationResetToken(String code);

    Optional<User> findUserByResetPasswordToken(String token);

    @Modifying
    @Query("delete from User usr where usr.id =:id")
    void deleteUserById(@Param("id") Long id);
}
