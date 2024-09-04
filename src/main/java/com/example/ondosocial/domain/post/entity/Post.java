package com.example.ondosocial.domain.post.entity;

import com.example.ondosocial.config.entity.BaseEntity;
import com.example.ondosocial.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Post extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int celsius;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(String title, String content, int celsius, User user) {
        this.title = title;
        this.content = content;
        this.celsius = celsius;
        this.user = user;
    }
}
