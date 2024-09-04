package com.example.ondosocial.domain.user.service;

import com.example.ondosocial.domain.user.config.PasswordEncoder;
import com.example.ondosocial.domain.user.dto.LoginRequestDto;
import com.example.ondosocial.domain.user.dto.SignupRequestDto;
import com.example.ondosocial.domain.user.dto.SignupResponseDto;
import com.example.ondosocial.domain.user.entity.User;
import com.example.ondosocial.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
    String email = signupRequestDto.getEmail();
    conditionalPassword(signupRequestDto.getPassword());//비밀번호 조건 검사
    String Password = passwordEncoder.encode(signupRequestDto.getPassword()); // 비밀번호 인코딩
    String name = signupRequestDto.getName();

    //중복된 사용자 아이디 확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 입니다.");
        }

        User user = new User(email, Password, name, false);

        User savedUser = userRepository.save(user);   //사용자 정보 저장
        return new SignupResponseDto(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getCreatedAt(),
                savedUser.getUpdatedAt()
        );
    }

    @Transactional
    public void login(LoginRequestDto loginRequestDto) {
    String email = loginRequestDto.getEmail();
    String password = loginRequestDto.getPassword();
    //아이디 확인
    User user = userRepository.findByEmail(email).orElseThrow(()->
            new IllegalArgumentException("등록된 ID가 존재하지 않습니다."));
    //비밀번호 확인
    if(!passwordEncoder.matches(password, user.getPassword())){
        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }
    }

    @Transactional
    public void delete(Long id, String password) {
        User user = userRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 ID 입니다."));
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        if(user.isDeleted()){
            throw new IllegalArgumentException("이미 탈퇴한 사용자 ID 입니다.");
        }
        user.Reuseid();
    }

    public void conditionalPassword(String password){
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z0-9$@$!%*#?&]{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        if(!pattern.matcher(password).matches()){
            throw new IllegalArgumentException("대소문자, 숫자, 특수문자를 포함한 8자 이상의 비밀번호를 설정해 주세요. ");
        }
    }

}
