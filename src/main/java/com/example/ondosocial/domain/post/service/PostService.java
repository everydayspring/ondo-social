package com.example.ondosocial.domain.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ondosocial.config.check.Check;
import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.domain.follow.entity.Follow;
import com.example.ondosocial.domain.post.dto.GetPostsDto;
import com.example.ondosocial.domain.post.entity.Post;
import com.example.ondosocial.domain.post.repository.PostRepository;
import com.example.ondosocial.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final Check check;

    public void create(Long id, String title, String content, int celsius) {
        User user = check.validateUserExists(id);

        postRepository.save(new Post(title, content, celsius, user));
    }

    @Transactional(readOnly = true)
    public Page<GetPostsDto.Response> getPosts(int page, int size) {
        Pageable pageable = createPage(page, size);

        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(GetPostsDto.Response::new);
    }

    @Transactional(readOnly = true)
    public Page<GetPostsDto.Response> getFollowerPosts(Long id, int page, int size) {
        User user = check.validateUserExists(id);

        List<Follow> follows = user.getFollows();
        List<Long> followerIds = new ArrayList<>();

        for (Follow follow : follows) {
            followerIds.add(follow.getFollower().getId());
        }
        followerIds.add(id);

        Pageable pageable = createPage(page, size);

        Page<Post> posts = postRepository.findAllByUserIdIn(followerIds, pageable);

        return posts.map(GetPostsDto.Response::new);
    }

    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        return postRepository
                .findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(ErrorCode.POST_NOT_FOUND.getMessage()));
    }

    public Post update(Long userId, Long postId, String title, String content, int celsius) {
        User user = check.validateUserExists(userId);

        Post post = check.postPermission(user, postId);

        post.update(title, content, celsius);

        return post;
    }

    public void delete(Long userId, Long postId) {
        User user = check.validateUserExists(userId);

        Post post = check.postPermission(user, postId);

        postRepository.delete(post);
    }

    private Pageable createPage(int page, int size) {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
