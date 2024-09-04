package com.example.ondosocial.domain.post.service;

import com.example.ondosocial.domain.follower.entity.Follower;
import com.example.ondosocial.domain.follower.repository.FollowerRepository;
import com.example.ondosocial.domain.post.dto.GetPostDto;
import com.example.ondosocial.domain.post.dto.GetPostsDto;
import com.example.ondosocial.domain.post.entity.Post;
import com.example.ondosocial.domain.post.repository.PostRepository;
import com.example.ondosocial.domain.user.entity.User;
import com.example.ondosocial.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;

    public void create(Long id, String title, String content, int celsius) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.isDeleted()) {
            throw new IllegalArgumentException("User is deleted");
        }
        Post post = new Post(title, content, celsius, user);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Page<GetPostsDto.Response> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(GetPostsDto.Response::new);
    }

    @Transactional(readOnly = true)
    public Page<GetPostsDto.Response> getPostsByFollowedUser(Long id, int page, int size) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.isDeleted()) {
            throw new IllegalArgumentException("User is deleted");
        }

        List<Follower> followers = followerRepository.findAllByUserId(id);
        List<Long> followerIds = new ArrayList<>();

        for (Follower follower : followers) {
            followerIds.add(follower.getFollower().getId());
        }
        followerIds.add(id);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Post> posts = postRepository.findAllByUserIdIn(followerIds, pageable);
        return posts.map(GetPostsDto.Response::new);
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }
}
