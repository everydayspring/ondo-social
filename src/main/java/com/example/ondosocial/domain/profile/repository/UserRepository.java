package com.example.ondosocial.domain.profile.repository;

import com.example.ondosocial.domain.profile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
