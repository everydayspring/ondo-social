package com.example.ondosocial.domain.user.controller;

import com.example.ondosocial.domain.user.dto.DeleteRequestDto;
import com.example.ondosocial.domain.user.dto.LoginRequestDto;
import com.example.ondosocial.domain.user.dto.SignupRequestDto;
import com.example.ondosocial.domain.user.dto.SignupResponseDto;
import com.example.ondosocial.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserCotroller {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){
       userService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestBody DeleteRequestDto deleteRequestDto){
        userService.delete(id, deleteRequestDto.getPassword());
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

