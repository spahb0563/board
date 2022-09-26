package com.example.board.controller;


import com.example.board.model.network.dto.post.PostCreateRequestDto;
import com.example.board.model.network.dto.post.PostResponseDto;
import com.example.board.model.network.dto.post.PostUpdateRequestDto;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("v1/post/{id}")
    public Long delete(@PathVariable Long id) {
        return postService.delete(id);
    }//delete() end
}
