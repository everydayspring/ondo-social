package com.example.ondosocial.domain.post.entity;

import com.example.ondosocial.config.entity.BaseEntity;
import com.example.ondosocial.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
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
    private String celsius;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(String title, String content, String celsius, User user) {
        this.title = title;
        this.content = content;
        this.celsius = celsius;
        this.user = user;
    }
}
