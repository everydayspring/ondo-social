package com.example.ondosocial.domain.follower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ondosocial.domain.follower.entity.Follower;
import com.example.ondosocial.domain.user.entity.User;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findAllByUser(User user);

    Follower findOneByUserAndFollower(User user, User follower);

    void deleteAllByUserOrFollower(User user, User follower);
}
