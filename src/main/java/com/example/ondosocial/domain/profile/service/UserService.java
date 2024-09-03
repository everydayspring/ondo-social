package com.example.ondosocial.domain.profile.service;


import com.example.ondosocial.domain.profile.dto.request.UserUpdateRequestDto;
import com.example.ondosocial.domain.profile.dto.response.UserProfileResponseDto;
import com.example.ondosocial.domain.profile.dto.response.UserUpdateResponseDto;
import com.example.ondosocial.domain.profile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.ondosocial.domain.profile.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<UserProfileResponseDto> getUser(Long id) {
        User foundUser=userRepository.findById(id)
                .orElseThrow(()-> new NullPointerException("해당 회원이 없습니다"));

        UserProfileResponseDto userProfileDto=new UserProfileResponseDto(
                foundUser.getId(),
                foundUser.getName(),
                foundUser.getEmail()
        );

        return ResponseEntity.status(HttpStatus.OK).body(userProfileDto);
    }

    @Transactional
    public ResponseEntity<UserUpdateResponseDto> updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        User user=userRepository.findById(id)
                .orElseThrow(()-> new NullPointerException("해당 회원이 없습니다"));

        //비밀번호 일치 확인
        if(!userUpdateRequestDto.getPassword().equals(user.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        user.update(userUpdateRequestDto.getName(),userUpdateRequestDto.getEmail());

        UserUpdateResponseDto userUpdateDto=new UserUpdateResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );

        return ResponseEntity.status(HttpStatus.OK).body(userUpdateDto);
    }
}
