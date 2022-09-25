package com.example.board.controller;


import com.example.board.model.network.PaginationDto;
import com.example.board.model.network.dto.post.PostCreateRequestDto;
import com.example.board.model.network.dto.post.PostListResponseDto;
import com.example.board.model.network.dto.post.PostResponseDto;
import com.example.board.model.network.dto.post.PostUpdateRequestDto;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/{categoryType}/post")
public class PostApiController {

    private final PostService postService;

//    @PostMapping("")
    public Long create(@RequestBody PostCreateRequestDto postCreateRequestDto) {
        return postService.create(postCreateRequestDto);
    }//create() end
//    @GetMapping("{id}")
    public PostResponseDto read(@PathVariable Long id) {
        return postService.read(id);
    }//read() end

//    @PutMapping("")
    public Long update(@RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        return postService.update(postUpdateRequestDto);
    }//update() end

//    @DeleteMapping("{id}")
    public Long delete(@PathVariable Long id) {
        return postService.delete(id);
    }//delete() end

//    @GetMapping("")
//    public PaginationDto<List<PostListResponseDto>> readAll(@PathVariable String CategoryType, @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
//        return postService.readAll(CategoryType, pageable);
//    }//readAll() end
}
