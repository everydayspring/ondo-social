package com.example.ondosocial.domain.follow.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ondosocial.config.check.Check;
import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.domain.follow.entity.Follow;
import com.example.ondosocial.domain.follow.repository.FollowerRepository;
import com.example.ondosocial.domain.user.entity.User;
import com.example.ondosocial.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowerService {
    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    private final Check check;

    public void create(Long id, Long followerId) {
        if (Objects.equals(id, followerId)) {
            throw new IllegalArgumentException(ErrorCode.FOLLOW_SELF_NOT_ALLOWED.getMessage());
        }

        User user = check.validateUserExists(id);
        User follower = check.validateUserExists(followerId);

        if (Objects.nonNull(followerRepository.findOneByUserAndFollower(user, follower))) {
            throw new IllegalArgumentException(ErrorCode.FOLLOWER_ALREADY_EXISTS.getMessage());
        }

        followerRepository.save(new Follow(user, follower));
    }

    @Transactional(readOnly = true)
    public List<Follow> getFollowers(Long id) {
        User user = check.validateUserExists(id);

        return followerRepository.findAllByUser(user);
    }

    public void delete(Long id, Long followerId) {
        User user = check.validateUserExists(id);
        User followerUser = check.validateUserExists(followerId);

        Follow follow = followerRepository.findOneByUserAndFollower(user, followerUser);

        followerRepository.delete(follow);
    }
}
