package com.example.ondosocial.domain.profile.service;


import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.config.password.PasswordEncoder;
import com.example.ondosocial.domain.profile.dto.request.ProfileUpdateRequestDto;
import com.example.ondosocial.domain.profile.dto.response.ProfileResponseDto;
import com.example.ondosocial.domain.profile.dto.response.ProfileUpdateResponseDto;
import com.example.ondosocial.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.ondosocial.domain.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ProfileResponseDto> getUser(Long id) {
        User foundUser=userRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));

        ProfileResponseDto userProfileDto=new ProfileResponseDto(
                foundUser.getId(),
                foundUser.getName(),
                foundUser.getEmail(),
                foundUser.getFollowers().size(),
                foundUser.getPosts().size()
        );

        return ResponseEntity.status(HttpStatus.OK).body(userProfileDto);
    }

    @Transactional
    public ResponseEntity<ProfileUpdateResponseDto> updateUser(Long id, ProfileUpdateRequestDto userUpdateRequestDto) {
        User user=userRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));

        //비밀번호 일치 확인
        if(!passwordEncoder.matches(userUpdateRequestDto.getCurrentPassword(), user.getPassword())){
            throw new IllegalArgumentException(ErrorCode.PASSWORD_MISMATCH.getMessage());
        }
        if(userUpdateRequestDto.getNewPassword()!=null){
            conditionalPassword(userUpdateRequestDto.getNewPassword()); //비밀번호 조건 검사
            if(userUpdateRequestDto.getNewPassword().equals(user.getPassword())){
                throw new IllegalArgumentException(ErrorCode.SAME_PASSWORD_NOT_ALLOWED.getMessage());
            }
        }

        user.update(userUpdateRequestDto,passwordEncoder);

        ProfileUpdateResponseDto userUpdateDto=new ProfileUpdateResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.OK).body(userUpdateDto);
    }
    //user의 Service와 공통부분
    public void conditionalPassword(String password){
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z0-9$@$!%*#?&]{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        if(!pattern.matcher(password).matches()){
            throw new IllegalArgumentException(ErrorCode.INVALID_PASSWORD_FORMAT.getMessage());
        }
    }
}
