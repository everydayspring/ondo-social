package com.example.ondosocial.domain.follower.controller;

import com.example.ondosocial.annotation.Auth;
import com.example.ondosocial.domain.follower.dto.FollowerCreateDto;
import com.example.ondosocial.domain.follower.dto.FollowerDeleteDto;
import com.example.ondosocial.domain.follower.dto.GetFollowersDto;
import com.example.ondosocial.domain.follower.entity.Follower;
import com.example.ondosocial.domain.follower.service.FollowerService;
import com.example.ondosocial.domain.user.dto.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/followers")
public class FollowerController {

    private final FollowerService followerService;

    /**
     * 친구 등록
     * @Headers Authorization
     * @body FollowerCreateDto.Request
     */
    @PostMapping
    public ResponseEntity<Void> create(@Auth AuthUser user, @RequestBody @Valid FollowerCreateDto.Request request) {
        followerService.create(user.getId(), request.getFollowerId());
        return ResponseEntity
                .ok()
                .build();
    }

    /**
     * 친구 조회
     * @Headers Authorization
     * @return List<GetFollowersDto.Response>
     */
    @GetMapping
    public ResponseEntity<List<GetFollowersDto.Response>> getFollowers(@Auth AuthUser user) {
        List<Follower> followers = followerService.getFollowers(user.getId());
        List<GetFollowersDto.Response> followerUsers = new ArrayList<>();

        for (Follower follower : followers) {
            followerUsers.add(new GetFollowersDto.Response(follower.getFollower()));
        }

        return ResponseEntity
                .ok(followerUsers);
    }

    /**
     * 친구 삭제
     * @Headers Authorization
     * @body FollowerDeleteDto.Request
     */
    @DeleteMapping
    public ResponseEntity<Void> delete(@Auth AuthUser user,@RequestBody @Valid FollowerDeleteDto.Request request) {
        followerService.delete(user.getId(), request.getFollowerId());
        return ResponseEntity
                .noContent()
                .build();
    }
}
