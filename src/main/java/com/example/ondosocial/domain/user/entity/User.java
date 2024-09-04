package com.example.ondosocial.domain.user.entity;

import com.example.ondosocial.config.entity.BaseEntity;
import com.example.ondosocial.domain.profile.dto.request.ProfileUpdateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean deleted;

    public User(String email, String password, String name, boolean deleted) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.deleted = deleted;
    }

    public void update(ProfileUpdateRequestDto userUpdateRequestDto) {
       if(userUpdateRequestDto.getName()!=null) {
           this.name=userUpdateRequestDto.getName();
       }
        if(userUpdateRequestDto.getEmail()!=null){
            this.email= userUpdateRequestDto.getEmail();
        }
        if(userUpdateRequestDto.getNewPassword()!=null){
            this.password=userUpdateRequestDto.getNewPassword();
        }
    }

}
