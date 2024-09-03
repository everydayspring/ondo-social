package com.example.ondosocial.domain.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.example.ondosocial.domain.user.dto.LoginRequestDto;
import com.example.ondosocial.domain.user.dto.SignupRequestDto;
import com.example.ondosocial.domain.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserCotroller {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "redirect:/login";
    }

    @PostMapping("/singin")
    public String login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        userService.login(loginRequestDto, response);
        return "redirect:/???"; // 수정 필요! 로그인 하면 무슨 화면이 떠야하는가!
    }

}

