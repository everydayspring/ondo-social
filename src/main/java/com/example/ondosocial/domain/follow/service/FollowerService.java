package com.example.ondosocial.domain.follow.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.config.validate.Preconditions;
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

    public void create(Long id, Long followerId) {
        Preconditions.validate(!Objects.equals(id, followerId), ErrorCode.FOLLOW_SELF_NOT_ALLOWED);

        User user = userRepository.findByIdOrElseThrow(id);

        Preconditions.validate(!user.isDeleted(), ErrorCode.DELETED_USER);

        User follower = userRepository.findByIdOrElseThrow(followerId);

        Preconditions.validate(!follower.isDeleted(), ErrorCode.DELETED_USER);

        Preconditions.validate(
                followerRepository.findOneByUserAndFollower(user, follower).isEmpty(),
                ErrorCode.FOLLOWER_ALREADY_EXISTS);

        followerRepository.save(new Follow(user, follower));
    }

    @Transactional(readOnly = true)
    public List<Follow> getFollowers(Long id) {
        User user = userRepository.findByIdOrElseThrow(id);

        Preconditions.validate(!user.isDeleted(), ErrorCode.DELETED_USER);

        return followerRepository.findAllByUser(user);
    }

    public void delete(Long id, Long followerId) {
        User user = userRepository.findByIdOrElseThrow(id);

        Preconditions.validate(!user.isDeleted(), ErrorCode.DELETED_USER);

        User follower = userRepository.findByIdOrElseThrow(followerId);

        Preconditions.validate(!follower.isDeleted(), ErrorCode.DELETED_USER);

        Follow follow = followerRepository.findOneByUserAndFollowerOrElseThrow(user, follower);

        followerRepository.delete(follow);
    }
}
