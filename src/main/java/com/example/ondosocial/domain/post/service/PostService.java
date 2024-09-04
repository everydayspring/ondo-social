package com.example.ondosocial.domain.post.service;

import com.example.ondosocial.domain.post.dto.PostRequestDto;
import com.example.ondosocial.domain.post.dto.PostResponseDto;
import com.example.ondosocial.domain.post.dto.PostSimpleResponseDto;
import com.example.ondosocial.domain.post.entity.Post;
import com.example.ondosocial.domain.post.repository.PostRepository;
import com.example.ondosocial.domain.user.entity.User;
import com.example.ondosocial.exception.AuthException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post(
                postRequestDto.getTitle(),
                postRequestDto.getContents(),
                postRequestDto.getCelsius(),
                postRequestDto.getUser()
        );

        Post createPost = postRepository.save(post);

        return new PostResponseDto(
                createPost.getTitle(),
                createPost.getContents(),
                createPost.getCelsius(),
                createPost.getCreatedAt(),
                createPost.getUpdatedAt()
        );
    }

    //게시물 아이디 단건 조회
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    //게시물 전체조회
    public List<PostSimpleResponseDto> getPosts(String date) {
        LocalDateTime postDateTime = LocalDate.parse(date).atStartOfDay();
        LocalDateTime lastModifiedTime = LocalDate.parse(date).atTime(LocalTime.MAX);

        List<Post> postList = postRepository.findAllByCreatedAtBetweenOrderByUpdatedAtDesc(postDateTime,lastModifiedTime);

        List<PostSimpleResponseDto> postsList = new ArrayList<>();
        for (Post post : postList) {
            PostSimpleResponseDto dto = new PostSimpleResponseDto(
                    post.getTitle(),
                    post.getContents(),
                    post.getCelsius(),
                    post.getCreatedAt(),
                    post.getUpdatedAt()

            );
            postsList.add(dto);

        }
        return postsList;
    }


    // 수정 권한 없는 경우
    public void updatePost() throws AuthException {
        if (false) {
            throw new AuthException();
        }
    }
    //삭제 권한 없는 경우
    public void deletePost() throws AuthException {
        if (false) {
            throw new AuthException();
        }
    }


}
