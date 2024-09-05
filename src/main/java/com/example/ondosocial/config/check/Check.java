package com.example.ondosocial.config.check;

import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.config.password.PasswordEncoder;
import com.example.ondosocial.domain.post.entity.Post;
import com.example.ondosocial.domain.post.repository.PostRepository;
import com.example.ondosocial.domain.user.entity.User;
import com.example.ondosocial.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Check {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final PasswordEncoder passwordEncoder;

    public User validateUserExists(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));

        if (user.isDeleted()) {
            throw new IllegalArgumentException(ErrorCode.DELETED_USER.getMessage());
        }

        return user;
    }

    public User validateUserExists(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));

        if (user.isDeleted()) {
            throw new IllegalArgumentException(ErrorCode.DELETED_USER.getMessage());
        }

        return user;
    }

    public void passwordMatch(String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException(ErrorCode.PASSWORD_MISMATCH.getMessage());
        }
    }

    public void validateEmail(String email) {
        Optional<User> checkUser = userRepository.findByEmail(email);

        if (checkUser.isPresent() && checkUser.get().isDeleted()) {
            throw new IllegalArgumentException(ErrorCode.DELETED_USER.getMessage());
        }
        if (checkUser.isPresent()) {
            throw new IllegalArgumentException(ErrorCode.ALREADY_SIGNED_UP_USER.getMessage());
        }
    }

    public Post postPermission(User user, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException(ErrorCode.POST_NOT_FOUND.getMessage()));

        if(!Objects.equals(user, post.getUser())) {
            throw new IllegalArgumentException(ErrorCode.NO_PERMISSION_TO_POST.getMessage());
        }

        return post;
    }
}
