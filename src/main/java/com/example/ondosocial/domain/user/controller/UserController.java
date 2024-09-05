package com.example.ondosocial.domain.user.controller;

import java.net.URI;

import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ondosocial.annotation.Auth;
import com.example.ondosocial.domain.user.dto.*;
import com.example.ondosocial.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 회원가입
     *
     * @body SignupDto.Request
     * @return Authorization Header
     */
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupDto.Request request) {
        String bearerToken =
                userService.signup(request.getEmail(), request.getPassword(), request.getName());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, bearerToken).build();
    }

    /**
     * 로그인
     *
     * @body SigninDto.Request
     * @return Authorization Header
     */
    @PostMapping("/signin")
    public ResponseEntity<Void> signin(@RequestBody @Valid SigninDto.Request request) {
        String bearerToken = userService.signin(request.getEmail(), request.getPassword());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, bearerToken).build();
    }

    /**
     * 프로필 조회 @Headers Authorization
     *
     * @return GetUserDto.DetailResponse
     */
    @GetMapping("/users")
    public ResponseEntity<GetUserDto.DetailResponse> getUser(@Auth AuthUser authUser) {
        GetUserDto.ServiseResponse response = userService.getUser(authUser.getId());

        return ResponseEntity.ok(
                new GetUserDto.DetailResponse(response.getAverageCelsius(), response.getUser()));
    }

    /**
     * 프로필 조회 @Headers Authorization
     *
     * @param id
     * @return GetUserDto.SimpleResponse
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<GetUserDto.SimpleResponse> getUser(
            @Auth AuthUser authUser, @PathVariable Long id) {
        // 사용자 본인의 id일 경우 상세조회로 redirect
        if (authUser.getId().equals(id)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/users"));

            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        }

        GetUserDto.ServiseResponse response = userService.getUser(authUser.getId());

        return ResponseEntity.ok(
                new GetUserDto.SimpleResponse(response.getAverageCelsius(), response.getUser()));
    }

    /**
     * 프로필 수정 @Headers Authorization
     *
     * @body UserUpdateDto.Request
     */
    @PatchMapping("/users")
    public ResponseEntity<Void> update(
            @Auth AuthUser authUser, @RequestBody @Valid UserUpdateDto.Request request) {
        userService.update(
                authUser.getId(),
                request.getEmail(),
                request.getName(),
                request.getPassword(),
                request.getNewPassword());

        return ResponseEntity.noContent().build();
    }

    /**
     * 회원탈퇴 @Headers Authorization
     *
     * @body UserDeleteDto.Request
     */
    @DeleteMapping("/users")
    public ResponseEntity<Void> delete(
            @Auth AuthUser authUser, @RequestBody UserDeleteDto.Request request) {
        userService.delete(authUser.getId(), request.getPassword());

        return ResponseEntity.noContent().build();
    }
}
