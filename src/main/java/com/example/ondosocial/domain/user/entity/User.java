package com.example.ondosocial.domain.user.entity;

import com.example.ondosocial.config.entity.BaseEntity;
import com.example.ondosocial.config.password.PasswordEncoder;
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

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void delete() {
        this.deleted = true;

    }


    public void update(ProfileUpdateRequestDto userUpdateRequestDto, PasswordEncoder passwordEncoder) {
       if(userUpdateRequestDto.getName()!=null) {
           this.name=userUpdateRequestDto.getName();
       }
        if(userUpdateRequestDto.getEmail()!=null){
            this.email= userUpdateRequestDto.getEmail();
        }
        if(userUpdateRequestDto.getNewPassword()!=null){
            //새로운 비밀번호 암호화 후 저장
            this.password=passwordEncoder.encode(userUpdateRequestDto.getNewPassword());
        }
    }

}
