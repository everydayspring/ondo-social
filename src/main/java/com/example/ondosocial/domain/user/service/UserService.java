package com.example.ondosocial.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ondosocial.config.auth.JwtUtil;
import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.config.log.Log;
import com.example.ondosocial.config.password.PasswordEncoder;
import com.example.ondosocial.config.validate.Preconditions;
import com.example.ondosocial.domain.follow.repository.FollowerRepository;
import com.example.ondosocial.domain.post.repository.PostRepository;
import com.example.ondosocial.domain.user.entity.User;
import com.example.ondosocial.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;
    private final PostRepository postRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String signup(String email, String password, String name) {
        Preconditions.validate(
                userRepository.findByEmailAndDeletedFalse(email).isEmpty(),
                ErrorCode.ALREADY_SIGNED_UP_USER);
        Preconditions.validate(
                userRepository.findByEmailAndDeletedTrue(email).isEmpty(), ErrorCode.DELETED_USER);

        User user = new User(email, passwordEncoder.encode(password), name);

        Log.success(this.getClass().getName());

        return jwtUtil.createToken(userRepository.save(user).getId(), email);
    }

    @Transactional(readOnly = true)
    public String signin(String email, String password) {
        User user = userRepository.findByEmailOrElseThrow(email);

        Preconditions.validate(!user.isDeleted(), ErrorCode.DELETED_USER);

        Preconditions.validate(
                passwordEncoder.matches(password, user.getPassword()), ErrorCode.PASSWORD_MISMATCH);

        Log.success(this.getClass().getName());

        return jwtUtil.createToken(user.getId(), user.getEmail());
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findByIdOrElseThrow(id);
    }

    public void update(Long id, String email, String name, String password, String newPassword) {
        User user = userRepository.findByIdOrElseThrow(id);

        Preconditions.validate(!user.isDeleted(), ErrorCode.DELETED_USER);

        Preconditions.validate(
                passwordEncoder.matches(password, user.getPassword()), ErrorCode.PASSWORD_MISMATCH);

        Preconditions.validate(
                user.getEmail().equals(email)
                        || userRepository.findByEmailAndDeletedFalse(email).isEmpty(),
                ErrorCode.ALREADY_SIGNED_UP_USER);
        Preconditions.validate(
                user.getEmail().equals(email)
                        || userRepository.findByEmailAndDeletedTrue(email).isEmpty(),
                ErrorCode.DELETED_USER);

        if (!newPassword.isBlank()) {
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                throw new IllegalArgumentException(
                        ErrorCode.SAME_PASSWORD_NOT_ALLOWED.getMessage());
            }

            user.update(email, passwordEncoder.encode(newPassword), name);
        } else {
            user.update(email, name);
        }

        Log.success(this.getClass().getName());
    }

    public void delete(Long id, String password) {
        User user = userRepository.findByIdOrElseThrow(id);

        Preconditions.validate(!user.isDeleted(), ErrorCode.DELETED_USER);

        Preconditions.validate(
                passwordEncoder.matches(password, user.getPassword()), ErrorCode.PASSWORD_MISMATCH);

        followerRepository.deleteAllByUserOrFollower(user, user);
        postRepository.deleteAllByUserId(id);

        user.delete();

        Log.success(this.getClass().getName());
    }
}
