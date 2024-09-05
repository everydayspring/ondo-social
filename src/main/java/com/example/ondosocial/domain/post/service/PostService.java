package com.example.ondosocial.domain.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.config.validate.Preconditions;
import com.example.ondosocial.domain.follow.entity.Follow;
import com.example.ondosocial.domain.post.dto.GetPostsDto;
import com.example.ondosocial.domain.post.entity.Post;
import com.example.ondosocial.domain.post.repository.PostRepository;
import com.example.ondosocial.domain.user.entity.User;
import com.example.ondosocial.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void create(Long id, String title, String content, int celsius) {
        User user = userRepository.findByIdOrElseThrow(id);

        Preconditions.validate(!user.isDeleted(), ErrorCode.DELETED_USER);

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
        User user = userRepository.findByIdOrElseThrow(id);

        Preconditions.validate(!user.isDeleted(), ErrorCode.DELETED_USER);

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
        return postRepository.findByIdOrElseThrow(id);
    }

    public Post update(Long userId, Long postId, String title, String content, int celsius) {
        User user = userRepository.findByIdOrElseThrow(userId);

        Preconditions.validate(!user.isDeleted(), ErrorCode.DELETED_USER);

        Post post = postRepository.findByIdOrElseThrow(postId);

        Preconditions.validate(
                Objects.equals(user, post.getUser()), ErrorCode.NO_PERMISSION_TO_POST);
        post.update(title, content, celsius);

        return post;
    }

    public void delete(Long userId, Long postId) {
        User user = userRepository.findByIdOrElseThrow(userId);

        Preconditions.validate(!user.isDeleted(), ErrorCode.DELETED_USER);

        Post post = postRepository.findByIdOrElseThrow(postId);

        Preconditions.validate(
                Objects.equals(user, post.getUser()), ErrorCode.NO_PERMISSION_TO_POST);

        postRepository.delete(post);
    }

    private Pageable createPage(int page, int size) {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
