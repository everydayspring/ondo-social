package com.example.ondosocial.domain.follower.entity;

import com.example.ondosocial.config.entity.BaseEntity;
import com.example.ondosocial.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Follower extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    public Follower(User user, User follower) {
        this.user = user;
        this.follower = follower;
    }
}
