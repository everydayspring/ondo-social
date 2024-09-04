package com.example.ondosocial.domain.post.controller;

import com.example.ondosocial.domain.post.dto.PostRequestDto;
import com.example.ondosocial.domain.post.dto.PostResponseDto;
import com.example.ondosocial.domain.post.dto.PostSimpleResponseDto;
import com.example.ondosocial.domain.post.entity.Post;
import com.example.ondosocial.domain.post.service.PostService;
import com.example.ondosocial.exception.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private final PostService postService;

    //게시물 작성
    @PostMapping
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto) {
        return postService.createPost(postRequestDto);

    }

    //게시물 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //게시물 전체 조회
    @GetMapping("/{Id}") //
    public List<PostSimpleResponseDto> getPosts(
            @PathVariable String Id){
        return postService.getPosts(Id);
    }

    //게시글 수정
    @PatchMapping("/{Id}")
    public ResponseEntity updatePost(
            HttpServletRequest request, @PathVariable("Id") String postId) {

        String userid = "";

        try {
            postService.updatePost();
        } catch (AuthException e) {
            return ResponseEntity.status(403).body("수정 권한이 없습니다");
        }

        return ResponseEntity.ok("수정 완료");
    }

    //게시글 삭제
    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deletePost(HttpServletRequest request, @PathVariable("Id") String postId) {

        String userid = "";

        try {
            postService.deletePost();
        } catch (AuthException e) {
            return ResponseEntity.status(403).body("삭제 권한이 없습니다");
        }

        return ResponseEntity.ok("삭제 완료");
    }

}
