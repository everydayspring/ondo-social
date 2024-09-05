package com.example.ondosocial.domain.follow.repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.domain.follow.entity.Follow;
import com.example.ondosocial.domain.user.entity.User;

public interface FollowerRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByUser(User user);

    Optional<Follow> findOneByUserAndFollower(User user, User follower);

    default Follow findOneByUserAndFollowerOrElseThrow(User user, User follower) {
        return findOneByUserAndFollower(user, follower)
                .orElseThrow(
                        () -> new NoSuchElementException(ErrorCode.FOLLOW_NOT_FOUND.getMessage()));
    }

    void deleteAllByUserOrFollower(User user, User follower);
}
