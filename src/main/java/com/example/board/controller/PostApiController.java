package com.example.board.controller;


import com.example.board.config.auth.LoginUser;
import com.example.board.config.auth.dto.SessionUser;
import com.example.board.model.network.dto.post.*;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class PostApiController {

    private final PostService postService;

    @PostMapping("v1/post")
    public Long create(@RequestBody PostCreateRequestDto postCreateRequestDto) {
        return postService.create(postCreateRequestDto);
    }//create() end
    @GetMapping("v1/post/{id}")
    public PostResponseDto read(@PathVariable Long id) {
        return postService.read(id);
    }//read() end

    @PutMapping("v1/post")
    public Long update(@RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        return postService.update(postUpdateRequestDto);
    }//update() end

    @PutMapping("v1/post/{id}/like")
    public int like(@LoginUser SessionUser user, @PathVariable Long id) {
        return postService.like(id, user.getId().toString());
    }//like() end

    @PutMapping("v1/post/{id}/dislike")
    public int dislike(@LoginUser SessionUser user, @PathVariable Long id) {
        return postService.dislike(id, user.getId().toString());
    }//dislike() end

    @DeleteMapping("v1/post/{id}")
    public Long delete(@PathVariable Long id) {
        return postService.delete(id);
    }//delete() end

//    @GetMapping("v1/post/best")
//    public List<PostBestListResponseDto> readTop10() {
//        return postService.readTop40(LocalDateTime.now().with(LocalTime.MIN));
//    }
}
