package com.example.board.controller;


import com.example.board.model.network.dto.post.PostCreateRequestDto;
import com.example.board.model.network.dto.post.PostResponseDto;
import com.example.board.model.network.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class OpinionApiController {

    @PostMapping("v1/opinion")
    public Long create(@RequestBody OpinionCreateRequestDto opinionCreateRequestDto) {
        return opinionService.create(opinionCreateRequestDto);
    }//create() end
    @GetMapping("v1/opinion/{id}")
    public PostResponseDto read(@PathVariable Long id) {
        return opinionService.read(id);
    }//read() end

    @PutMapping("v1/opinion")
    public Long update(@RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        return postService.update(postUpdateRequestDto);
    }//update() end

    @DeleteMapping("v1/opinion/{id}")
    public Long delete(@PathVariable Long id) {
        return postService.delete(id);
    }//delete() end


}
