package com.example.ondosocial.domain.follower.controller;

import com.example.ondosocial.domain.follower.dto.FollowerCreateDto;
import com.example.ondosocial.domain.follower.dto.GetFollowersDto;
import com.example.ondosocial.domain.follower.entity.Follower;
import com.example.ondosocial.domain.follower.service.FollowerService;
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
@RequestMapping("/users/{id}/followers")
public class FollowerController {

    private final FollowerService followerService;

    @PostMapping
    public ResponseEntity<Objects> create(@PathVariable Long id, @RequestBody @Valid FollowerCreateDto.Request request) {
        followerService.create(id, request.getFollowerId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<GetFollowersDto.Response>> getFollowers(@PathVariable Long id) {
        List<Follower> followers = followerService.getFollowers(id);
        List<GetFollowersDto.Response> followerUsers = new ArrayList<>();

        for (Follower follower : followers) {
            followerUsers.add(new GetFollowersDto.Response(follower.getFollower()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(followerUsers);
    }

    @DeleteMapping("/{followerId}")
    public ResponseEntity<Objects> delete(@PathVariable Long id, @PathVariable Long followerId) {
        followerService.delete(id, followerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
