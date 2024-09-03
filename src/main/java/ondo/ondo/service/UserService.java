package ondo.ondo.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ondo.ondo.config.PasswordEncoder;
import ondo.ondo.dto.LoginRequestDto;
import ondo.ondo.dto.SignupRequestDto;
import ondo.ondo.entity.User;
import ondo.ondo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
    String email = signupRequestDto.getEmail();
    String password = passwordEncoder.encode(signupRequestDto.getPassword());
    String username = signupRequestDto.getUsername();

    //중복된 사용자 아이디 확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 아이디 입니다.");
        }

        User user = new User(email, password, username);
        userRepository.save(user);
    }

    public void conditionalPassword(String password){
        if(password.length() >= 8){
            throw new IllegalArgumentException("비밀번호는 최소 8글자 이상 입니다.");
        }
        if()
    }


    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

    }
}
