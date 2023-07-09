package com.catchthemoment.repository;

import com.catchthemoment.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findUserById(Long userId);

    @Query(value = "select usr from User usr where usr.email = :email")
    @EntityGraph(value = "user-graph", type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"albums"})
    Optional<User> findUserByEmail(@Param("email") String email);

    @EntityGraph(value = "user-graph", attributePaths = {"albums"},type = EntityGraph.EntityGraphType.LOAD)
    @Query("select usr from User usr where usr.confirmationResetToken= :token")
    Optional<User> findUsersByConfirmationResetToken(@Param("token") String token);

    @EntityGraph(value = "user-graph",attributePaths = {"albums"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select u from User u where u.name = :name")
    Optional<User> findUsersByName(@Param("name") String name);


    Optional<User> findUserByPassword(String password);

    @Modifying
    @Query("delete from User usr where usr.id =:id")
    @EntityGraph(value = "user-graph", attributePaths = {"albums"}, type = EntityGraph.EntityGraphType.LOAD)
    void deleteUserById(@Param("id") Long id);
}

