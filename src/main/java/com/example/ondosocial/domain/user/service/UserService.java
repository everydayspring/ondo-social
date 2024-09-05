package com.example.ondosocial.domain.user.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ondosocial.config.auth.JwtUtil;
import com.example.ondosocial.config.check.Check;
import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.config.password.PasswordEncoder;
import com.example.ondosocial.domain.follower.repository.FollowerRepository;
import com.example.ondosocial.domain.post.repository.PostRepository;
import com.example.ondosocial.domain.user.entity.User;
import com.example.ondosocial.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;
    private final PostRepository postRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final Check check;

    public String signup(String email, String password, String name) {
        check.validateEmail(email);

        User user = new User(email, passwordEncoder.encode(password), name);

        return jwtUtil.createToken(userRepository.save(user).getId(), email);
    }

    @Transactional(readOnly = true)
    public String signin(String email, String password) {
        User user = check.validateUserExists(email);

        check.passwordMatch(password, user);

        return jwtUtil.createToken(user.getId(), user.getEmail());
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    public void update(Long id, String email, String name, String password, String newPassword) {
        User user = check.validateUserExists(id);

        check.passwordMatch(password, user);

        if (!user.getEmail().equals(email)) {
            check.validateEmail(email);
        }

        if (!newPassword.isBlank()) {
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                throw new IllegalArgumentException(
                        ErrorCode.SAME_PASSWORD_NOT_ALLOWED.getMessage());
            }

            user.update(email, passwordEncoder.encode(newPassword), name);
        } else {
            user.update(email, name);
        }
    }

    public void delete(Long id, String password) {
        User user = check.validateUserExists(id);

        check.passwordMatch(password, user);

        followerRepository.deleteAllByUserOrFollower(user, user);
        postRepository.deleteAllByUserId(id);

        user.delete();
    }
}
