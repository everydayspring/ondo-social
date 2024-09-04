package com.example.ondosocial.domain.post.controller;

import com.example.ondosocial.annotation.Auth;
import com.example.ondosocial.domain.post.dto.GetPostDto;
import com.example.ondosocial.domain.post.dto.GetPostsDto;
import com.example.ondosocial.domain.post.dto.PostCreateDto;
import com.example.ondosocial.domain.post.dto.PostUpdateDto;
import com.example.ondosocial.domain.post.entity.Post;
import com.example.ondosocial.domain.post.service.PostService;
import com.example.ondosocial.domain.user.dto.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> create(@Auth AuthUser user, @RequestBody @Valid PostCreateDto.Request request) {
        postService.create(user.getId(), request.getTitle(), request.getContent(), request.getCelsius());
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping
    public ResponseEntity<Page<GetPostsDto.Response>> getposts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .ok(postService.getPosts(page - 1, size));
    }

    @GetMapping("/followers")
    public ResponseEntity<Page<GetPostsDto.Response>> getpostsByFollower(@Auth AuthUser user, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .ok(postService.getPostsByFollowedUser(user.getId(),page - 1, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostDto.Response> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity
                .ok(new GetPostDto.Response(post));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostUpdateDto.Response> update(@Auth AuthUser user, @PathVariable Long id, @RequestBody @Valid PostUpdateDto.Request request) {
        Post post = postService.update(user.getId(), id, request.getTitle(), request.getContent(), request.getCelsius());
        return ResponseEntity.ok(new PostUpdateDto.Response(post));
    }



}
