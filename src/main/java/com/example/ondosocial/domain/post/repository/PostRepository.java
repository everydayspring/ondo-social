package com.example.ondosocial.domain.post.repository;

import com.example.ondosocial.domain.post.entity.Post;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByUserIdIn(List<Long> id, Pageable pageable);
}
