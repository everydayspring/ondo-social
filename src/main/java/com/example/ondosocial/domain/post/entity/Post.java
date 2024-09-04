package com.example.ondosocial.domain.post.entity;

import com.example.ondosocial.config.entity.BaseEntity;
import com.example.ondosocial.domain.post.dto.PostRequestDto;
import com.example.ondosocial.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private int celsius;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(String title, String contents, int celsius, User user) {
        this.title = title;
        this.contents = contents;
        this.celsius = celsius;
        this.user = user;
    }

    public Post(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.celsius = requestDto.getCelsius();
        this.user = requestDto.getUser();
    }


}
