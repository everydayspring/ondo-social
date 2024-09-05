package com.example.ondosocial.domain.follow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ondosocial.domain.follow.entity.Follow;
import com.example.ondosocial.domain.user.entity.User;

public interface FollowerRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByUser(User user);

    Follow findOneByUserAndFollower(User user, User follower);

    void deleteAllByUserOrFollower(User user, User follower);
}
