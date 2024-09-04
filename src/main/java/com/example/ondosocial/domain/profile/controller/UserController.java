package com.example.ondosocial.domain.profile.controller;


import com.example.ondosocial.domain.profile.dto.request.UserUpdateRequestDto;
import com.example.ondosocial.domain.profile.dto.response.UserProfileResponseDto;
import com.example.ondosocial.domain.profile.dto.response.UserUpdateResponseDto;
import com.example.ondosocial.domain.profile.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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
    public ResponseEntity<UserUpdateResponseDto> updateUser(@PathVariable("id") Long id,
                                                            @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto){

        return userService.updateUser(id,userUpdateRequestDto);
    }

}
