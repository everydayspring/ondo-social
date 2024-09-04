package com.example.ondosocial.domain.profile.controller;


import com.example.ondosocial.domain.profile.dto.request.ProfileUpdateRequestDto;
import com.example.ondosocial.domain.profile.dto.response.ProfileResponseDto;
import com.example.ondosocial.domain.profile.dto.response.ProfileUpdateResponseDto;
import com.example.ondosocial.domain.profile.service.ProfileService;
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
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDto> getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    //프로필 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ProfileUpdateResponseDto> updateUser(@PathVariable("id") Long id,
                                                               @RequestBody @Valid ProfileUpdateRequestDto userUpdateRequestDto){

        return userService.updateUser(id,userUpdateRequestDto);
    }

}
