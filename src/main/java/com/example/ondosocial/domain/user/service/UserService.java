package com.example.ondosocial.domain.user.service;

import com.example.ondosocial.config.auth.JwtUtil;
import com.example.ondosocial.config.password.PasswordEncoder;
import com.example.ondosocial.domain.user.entity.User;
import com.example.ondosocial.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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
            throw new IllegalArgumentException("탈퇴한 사용자");
        }
        if (checkUser != null) {
            throw new IllegalArgumentException("이미 가입한 사용자");
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
            throw new IllegalArgumentException("사용자 없음");
        } else if (user.isDeleted()) {
            throw new IllegalArgumentException("탈퇴한 사용자");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.createToken(user.getId(), user.getEmail());
    }

    public void delete(Long id, String password) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 ID 입니다."));

        if (user.isDeleted()) {
            throw new IllegalArgumentException("이미 탈퇴한 사용자 ID 입니다.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        user.delete();
        userRepository.save(user);
    }

    public void conditionalPassword(String password) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z0-9$@$!%*#?&]{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        if (!pattern.matcher(password).matches()) {
            throw new IllegalArgumentException("대소문자, 숫자, 특수문자를 포함한 8자 이상의 비밀번호를 설정해 주세요. ");
        }
    }


}
