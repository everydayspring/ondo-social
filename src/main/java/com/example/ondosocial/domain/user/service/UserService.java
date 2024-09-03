package com.example.ondosocial.domain.user.service;


import com.example.ondosocial.domain.user.dto.request.UserUpdateRequestDto;
import com.example.ondosocial.domain.user.dto.response.UserProfileDto;
import com.example.ondosocial.domain.user.dto.response.UserProfileResponseDto;
import com.example.ondosocial.domain.user.dto.response.UserUpdateResponseDto;
import com.example.ondosocial.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.ondosocial.domain.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserProfileResponseDto getUser(Long id) {
        User foundUser=userRepository.findById(id)
                .orElseThrow(()-> new NullPointerException("해당 회원이 없습니다"));

        UserProfileDto userProfileDto=new UserProfileDto(
                foundUser.getId(),
                foundUser.getName(),
                foundUser.getEmail()
        );
        return new UserProfileResponseDto(
                "성공",
                200,
                userProfileDto
        );
    }

    @Transactional
    public UserUpdateResponseDto updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        User user=userRepository.findById(id)
                .orElseThrow(()-> new NullPointerException("해당 회원이 없습니다"));

        //비밀번호 일치 확인
        if(!userUpdateRequestDto.getPassword().equals(user.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }


        user.update(userUpdateRequestDto.getName(),userUpdateRequestDto.getEmail());

        return new UserUpdateResponseDto(
                " 성공",
                205,
                user.getId()
        );

    }
}
