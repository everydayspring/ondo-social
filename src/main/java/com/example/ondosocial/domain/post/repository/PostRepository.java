package com.example.ondosocial.domain.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ondosocial.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByUserIdIn(List<Long> id, Pageable pageable);

    void deleteAllByUserId(Long id);
}
