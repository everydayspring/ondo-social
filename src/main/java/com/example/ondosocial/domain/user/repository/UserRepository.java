package com.example.ondosocial.domain.user.repository;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    default User findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    Optional<User> findByEmail(String email);

    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email)
                .orElseThrow(
                        () -> new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    Optional<User> findByEmailAndDeletedTrue(String email);

    Optional<User> findByEmailAndDeletedFalse(String email);
}
