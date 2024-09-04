package com.example.ondosocial.domain.profile.controller;


import com.example.ondosocial.annotation.Auth;
import com.example.ondosocial.config.auth.AuthUserArgumentResolver;
import com.example.ondosocial.config.password.PasswordEncoder;
import com.example.ondosocial.domain.profile.dto.request.ProfileUpdateRequestDto;
import com.example.ondosocial.domain.profile.dto.response.ProfileResponseDto;
import com.example.ondosocial.domain.profile.dto.response.ProfileUpdateResponseDto;
import com.example.ondosocial.domain.profile.service.ProfileService;
import com.example.ondosocial.domain.user.dto.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class ProfileController {

    private final ProfileService userService;


    //프로필 조회
    @GetMapping
    public ResponseEntity<ProfileResponseDto> getUser(@Auth AuthUser authUser){
        return userService.getUser(authUser.getId());
    }

    //프로필 수정
    @PatchMapping
    public ResponseEntity<ProfileUpdateResponseDto> updateUser(@Auth AuthUser authUser,
                                                               @RequestBody @Valid ProfileUpdateRequestDto userUpdateRequestDto){

        return userService.updateUser(authUser.getId(),userUpdateRequestDto);
    }

}
