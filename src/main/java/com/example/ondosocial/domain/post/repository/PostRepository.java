package com.example.ondosocial.domain.post.repository;

import com.example.ondosocial.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCreatedAtBetweenOrderByUpdatedAtDesc(LocalDateTime postDateTime, LocalDateTime lastModifiedTime);


}
