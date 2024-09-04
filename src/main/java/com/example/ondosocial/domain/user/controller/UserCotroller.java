package com.example.ondosocial.domain.user.controller;

import com.example.ondosocial.annotation.Auth;
import com.example.ondosocial.domain.user.dto.*;
import com.example.ondosocial.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserCotroller {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequestDto request) {
        String bearerToken = userService.signup(request.getEmail(), request.getPassword(), request.getName());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .build();
    }

    @PostMapping("/signin")
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto request) {
        String bearerToken = userService.signin(request.getEmail(), request.getPassword());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .build();
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> delete(@Auth AuthUser authUser, @RequestBody DeleteRequestDto request) {
        userService.delete(authUser.getId(), request.getPassword());
        return ResponseEntity
                .noContent()
                .build();
    }

}

