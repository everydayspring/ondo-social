package com.example.ondosocial.domain.follower.service;

import com.example.ondosocial.domain.follower.entity.Follower;
import com.example.ondosocial.domain.follower.repository.FollowerRepository;
import com.example.ondosocial.domain.user.entity.User;
import com.example.ondosocial.domain.user.entity.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;


    public void create(Long id, Long followerId) {
        if(Objects.equals(id, followerId)) {
            throw new IllegalArgumentException("Users cannot follow themselves");
        }
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        User follower = userRepository.findById(followerId).orElseThrow(()-> new RuntimeException("User not found"));

        if(followerRepository.findOneByUserAndFollower(user, follower) != null) {
            throw new RuntimeException("Follower already exists");
        }

        followerRepository.save(new Follower(user, follower));
    }

    @Transactional(readOnly = true)
    public List<Follower> getFollowers(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));

        return followerRepository.findAllByUser(user);
    }


    public void delete(Long id, Long followerId) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        User followerUser = userRepository.findById(followerId).orElseThrow(()-> new RuntimeException("User not found"));

        Follower follower = followerRepository.findOneByUserAndFollower(user, followerUser);
        followerRepository.delete(follower);
    }
}
