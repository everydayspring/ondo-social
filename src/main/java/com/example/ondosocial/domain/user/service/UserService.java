package com.example.ondosocial.domain.user.service;

import com.example.ondosocial.config.auth.JwtUtil;
import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.config.password.PasswordEncoder;
import com.example.ondosocial.domain.user.entity.User;
import com.example.ondosocial.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String signup(String email, String password, String name) {
        emailCheck(email);

        User user = new User(email, passwordEncoder.encode(password), name);

        return jwtUtil.createToken(userRepository.save(user).getId(), email);
    }

    @Transactional(readOnly = true)
    public String signin(String email, String password) {
        User user = userCheck(email);

        passwordCheck(password, user);

        return jwtUtil.createToken(user.getId(), user.getEmail());
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    public void update(Long id, String email, String name, String password, String newPassword) {
        User user = userCheck(id);

        passwordCheck(password, user);

        if (!user.getEmail().equals(email)) {
            emailCheck(email);
        }

        if (!newPassword.isBlank()) {
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                throw new IllegalArgumentException(ErrorCode.SAME_PASSWORD_NOT_ALLOWED.getMessage());
            }

            user.update(email, passwordEncoder.encode(newPassword), name);
        } else {
            user.update(email, name);
        }
    }

    public void delete(Long id, String password) {
        User user = userCheck(id);

        passwordCheck(password, user);

        user.delete();
    }

    public User userCheck(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));

        if (user.isDeleted()) {
            throw new IllegalArgumentException(ErrorCode.DELETED_USER.getMessage());
        }

        return user;
    }

    public User userCheck(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));

        if (user.isDeleted()) {
            throw new IllegalArgumentException(ErrorCode.DELETED_USER.getMessage());
        }

        return user;
    }

    public void passwordCheck(String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException(ErrorCode.PASSWORD_MISMATCH.getMessage());
        }
    }

    public void emailCheck(String email) {
        Optional<User> checkUser = userRepository.findByEmail(email);

        if (checkUser.isPresent() && checkUser.get().isDeleted()) {
            throw new IllegalArgumentException(ErrorCode.DELETED_USER.getMessage());
        }
        if (checkUser.isPresent()) {
            throw new IllegalArgumentException(ErrorCode.ALREADY_SIGNED_UP_USER.getMessage());
        }
    }
}
