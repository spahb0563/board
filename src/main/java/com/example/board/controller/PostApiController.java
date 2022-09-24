package com.example.board.controller;


import com.example.board.model.network.PaginationDto;
import com.example.board.model.network.dto.post.PostCreateRequestDto;
import com.example.board.model.network.dto.post.PostListResponseDto;
import com.example.board.model.network.dto.post.PostResponseDto;
import com.example.board.model.network.dto.post.PostUpdateRequestDto;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostApiController {

    private final PostService postService;

    @PostMapping("")
    public Long create(@RequestBody PostCreateRequestDto postCreateRequestDto) {
        return postService.create(postCreateRequestDto);
    }
    @GetMapping("{id}")
    public PostResponseDto read(@PathVariable Long id) {
        return postService.read(id);
    }

    @PutMapping("{id}")
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        return postService.update(id, postUpdateRequestDto);
    }

//    @DeleteMapping("{id}")
//    public Long delete(@PathVariable Long id) {
//        postsService.delete(id);
//        return id;
//    }

    @GetMapping("{categoryType}")
    public PaginationDto<PostListResponseDto> readAll(@PathVariable String CategoryType, Pageable pageable) {
        return postService.readAll(CategoryType, pageable);
    }
}
