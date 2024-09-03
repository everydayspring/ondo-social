package com.example.ondosocial.domain.user.repository;

import com.example.ondosocial.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
