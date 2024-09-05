package com.example.ondosocial.domain.follower.service;

import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.domain.follower.entity.Follower;
import com.example.ondosocial.domain.follower.repository.FollowerRepository;
import com.example.ondosocial.domain.user.entity.User;
import com.example.ondosocial.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    public void create(Long id, Long followerId) {
        if(Objects.equals(id, followerId)) {
            throw new IllegalArgumentException(ErrorCode.FOLLOW_SELF_NOT_ALLOWED.getMessage());
        }

        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException(ErrorCode.POST_NOT_FOUND.getMessage()));
        if(user.isDeleted()) {
            throw new IllegalArgumentException(ErrorCode.DELETED_USER.getMessage());
        }

        User follower = userRepository.findById(followerId).orElseThrow(()-> new NoSuchElementException(ErrorCode.POST_NOT_FOUND.getMessage()));
        if(follower.isDeleted()) {
            throw new IllegalArgumentException(ErrorCode.DELETED_USER.getMessage());
        }

        if(followerRepository.findOneByUserAndFollower(user, follower) != null) {
            throw new IllegalArgumentException(ErrorCode.FOLLOWER_ALREADY_EXISTS.getMessage());
        }

        followerRepository.save(new Follower(user, follower));
    }

    @Transactional(readOnly = true)
    public List<Follower> getFollowers(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException(ErrorCode.POST_NOT_FOUND.getMessage()));

        return followerRepository.findAllByUser(user);
    }

    public void delete(Long id, Long followerId) {
        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException(ErrorCode.POST_NOT_FOUND.getMessage()));
        User followerUser = userRepository.findById(followerId).orElseThrow(()-> new NoSuchElementException(ErrorCode.POST_NOT_FOUND.getMessage()));

        Follower follower = followerRepository.findOneByUserAndFollower(user, followerUser);
        followerRepository.delete(follower);
    }
}
