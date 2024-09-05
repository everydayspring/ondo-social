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

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    /**
     * 게시물 등록
     *
     * @Headers Authorization
     * @body PostCreateDto.Request
     */
    @PostMapping
    public ResponseEntity<Void> create(@Auth AuthUser user, @RequestBody @Valid PostCreateDto.Request request) {
        postService.create(user.getId(), request.getTitle(), request.getContent(), request.getCelsius());

        return ResponseEntity
                .ok()
                .build();
    }

    /**
     * 게시물 전체 조회
     *
     * @return Page<GetPostsDto.Response>
     * @query page, size
     */
    @GetMapping
    public ResponseEntity<Page<GetPostsDto.Response>> getPosts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .ok(postService.getPosts(page - 1, size));
    }

    /**
     * 뉴스피드 조회
     *
     * @return Page<GetPostsDto.Response>
     * @Headers Authorization
     * @query page, size
     */
    @GetMapping("/followers")
    public ResponseEntity<Page<GetPostsDto.Response>> getFollowerPosts(@Auth AuthUser user, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .ok(postService.getFollowerPosts(user.getId(), page - 1, size));
    }

    /**
     * 게시물 단건 조회
     *
     * @param id
     * @return GetPostDto.Response
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetPostDto.Response> getPost(@PathVariable Long id) {
        Post post = postService.getPost(id);

        return ResponseEntity
                .ok(new GetPostDto.Response(post));
    }

    /**
     * 게시물 수정
     *
     * @param id
     * @return PostUpdateDto.Response
     * @Headers Authorization
     * @body PostUpdateDto.Request
     */
    @PatchMapping("/{id}")
    public ResponseEntity<PostUpdateDto.Response> update(@Auth AuthUser user, @PathVariable Long id, @RequestBody @Valid PostUpdateDto.Request request) {
        Post post = postService.update(user.getId(), id, request.getTitle(), request.getContent(), request.getCelsius());

        return ResponseEntity
                .ok(new PostUpdateDto.Response(post));
    }

    /**
     * 게시물 삭제
     *
     * @param id
     * @Headers Authorization
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Auth AuthUser user, @PathVariable Long id) {
        postService.delete(user.getId(), id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
