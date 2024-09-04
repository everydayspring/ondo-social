package com.example.ondosocial.domain.profile.repository;

import com.example.ondosocial.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<User,Long> {

}
