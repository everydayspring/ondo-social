package com.example.ondosocial.domain.profile.service;


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
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository userRepository;

    public ResponseEntity<ProfileResponseDto> getUser(Long id) {
        User foundUser=userRepository.findById(id)
                .orElseThrow(()-> new NullPointerException("해당 회원이 없습니다"));

        ProfileResponseDto userProfileDto=new ProfileResponseDto(
                foundUser.getId(),
                foundUser.getName(),
                foundUser.getEmail()
        );

        return ResponseEntity.status(HttpStatus.OK).body(userProfileDto);
    }

    @Transactional
    public ResponseEntity<ProfileUpdateResponseDto> updateUser(Long id, ProfileUpdateRequestDto userUpdateRequestDto) {
        User user=userRepository.findById(id)
                .orElseThrow(()-> new NullPointerException("해당 회원이 없습니다"));

        //비밀번호 일치 확인
        if(!userUpdateRequestDto.getCurrentPassword().equals(user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        if(userUpdateRequestDto.getNewPassword()!=null){
            conditionalPassword(userUpdateRequestDto.getNewPassword()); //비밀번호 조건 검사
            if(userUpdateRequestDto.getNewPassword().equals(user.getPassword())){
                throw new IllegalArgumentException("동일한 비밀번호로 변경할 수 없습니다");
            }
        }

        user.update(userUpdateRequestDto);

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
            throw new IllegalArgumentException("대소문자, 숫자, 특수문자를 포함한 8자 이상의 비밀번호를 설정해 주세요. ");
        }
    }
}
