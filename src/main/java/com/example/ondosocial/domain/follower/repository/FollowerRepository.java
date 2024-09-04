package com.example.ondosocial.domain.follower.repository;

import com.example.ondosocial.domain.follower.entity.Follower;
import com.example.ondosocial.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findAllByUser(User user);

    Follower findOneByUserAndFollower(User user, User follower);

    List<Follower> findAllByUserId(Long id);
}
