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
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String signup(String email, String password, String name) {

        User checkUser = userRepository.findByEmail(email);
        if (checkUser != null && checkUser.isDeleted()) {
            throw new IllegalArgumentException(ErrorCode.DELETED_USER.getMessage());
        }
        if (checkUser != null) {
            throw new IllegalArgumentException(ErrorCode.ALREADY_SIGNED_UP_USER.getMessage());
        }

        conditionalPassword(password);

        User user = new User(email, passwordEncoder.encode(password), name);

        User sevedUser = userRepository.save(user);
        return jwtUtil.createToken(sevedUser.getId(), user.getEmail());
    }

    @Transactional(readOnly = true)
    public String signin(String email, String password) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage());
        } else if (user.isDeleted()) {
            throw new IllegalArgumentException(ErrorCode.DELETED_USER.getMessage());
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException(ErrorCode.PASSWORD_MISMATCH.getMessage());
        }

        return jwtUtil.createToken(user.getId(), user.getEmail());
    }

    public void delete(Long id, String password) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));

        if (user.isDeleted()) {
            throw new IllegalArgumentException(ErrorCode.DELETED_USER.getMessage());
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException(ErrorCode.PASSWORD_MISMATCH.getMessage());
        }

        user.delete();
        userRepository.save(user);
    }

    public void conditionalPassword(String password) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z0-9$@$!%*#?&]{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        if (!pattern.matcher(password).matches()) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PASSWORD_FORMAT.getMessage());
        }
    }


}
