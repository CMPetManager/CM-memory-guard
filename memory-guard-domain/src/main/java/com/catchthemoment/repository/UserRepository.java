package com.catchthemoment.repository;

import com.catchthemoment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long userId);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUSerByConfirmationResetToken(String code);
}
