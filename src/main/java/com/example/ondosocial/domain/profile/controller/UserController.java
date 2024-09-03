package com.example.ondosocial.domain.profile.controller;


import com.example.ondosocial.domain.profile.dto.request.UserUpdateRequestDto;
import com.example.ondosocial.domain.profile.dto.response.UserProfileResponseDto;
import com.example.ondosocial.domain.profile.dto.response.UserUpdateResponseDto;
import com.example.ondosocial.domain.profile.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    //프로필 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    //프로필 수정
    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateResponseDto> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequestDto userUpdateRequestDto){
        return userService.updateUser(id,userUpdateRequestDto);
    }

}
