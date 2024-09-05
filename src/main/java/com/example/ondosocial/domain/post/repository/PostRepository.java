package com.example.ondosocial.domain.post.repository;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByUserIdIn(List<Long> id, Pageable pageable);

    void deleteAllByUserId(Long id);

    default Post findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(ErrorCode.POST_NOT_FOUND.getMessage()));
    }
}
