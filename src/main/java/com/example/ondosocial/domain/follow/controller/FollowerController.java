package com.example.ondosocial.domain.follow.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ondosocial.annotation.Auth;
import com.example.ondosocial.domain.follow.dto.FollowerCreateDto;
import com.example.ondosocial.domain.follow.dto.FollowerDeleteDto;
import com.example.ondosocial.domain.follow.dto.GetFollowersDto;
import com.example.ondosocial.domain.follow.entity.Follow;
import com.example.ondosocial.domain.follow.service.FollowerService;
import com.example.ondosocial.domain.user.dto.AuthUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/followers")
public class FollowerController {
    private final FollowerService followerService;

    /**
     * 친구 등록 @Headers Authorization
     *
     * @body FollowerCreateDto.Request
     */
    @PostMapping
    public ResponseEntity<Void> create(
            @Auth AuthUser user, @RequestBody @Valid FollowerCreateDto.Request request) {
        followerService.create(user.getId(), request.getFollowerId());

        return ResponseEntity.ok().build();
    }

    /**
     * 친구 조회
     *
     * @return List<GetFollowersDto.Response> @Headers Authorization
     */
    @GetMapping
    public ResponseEntity<List<GetFollowersDto.Response>> getFollowers(@Auth AuthUser user) {
        List<Follow> follows = followerService.getFollowers(user.getId());
        List<GetFollowersDto.Response> followerUsers = new ArrayList<>();

        for (Follow follow : follows) {
            followerUsers.add(new GetFollowersDto.Response(follow.getFollower()));
        }

        return ResponseEntity.ok(followerUsers);
    }

    /**
     * 친구 삭제 @Headers Authorization
     *
     * @body FollowerDeleteDto.Request
     */
    @DeleteMapping
    public ResponseEntity<Void> delete(
            @Auth AuthUser user, @RequestBody @Valid FollowerDeleteDto.Request request) {
        followerService.delete(user.getId(), request.getFollowerId());

        return ResponseEntity.noContent().build();
    }
}
