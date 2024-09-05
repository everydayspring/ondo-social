package com.example.ondosocial.domain.user.entity;

import com.example.ondosocial.config.entity.BaseEntity;
import com.example.ondosocial.config.password.PasswordEncoder;
import com.example.ondosocial.domain.follower.entity.Follower;
import com.example.ondosocial.domain.post.entity.Post;
import com.example.ondosocial.domain.profile.dto.request.ProfileUpdateRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    List<Follower> followers=new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    List<Post> posts=new ArrayList<>();

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
