package com.example.board.controller;


import com.example.board.model.network.dto.opinion.OpinionCreateRequestDto;
import com.example.board.model.network.dto.opinion.OpinionResponseDto;
import com.example.board.model.network.dto.opinion.OpinionUpdateRequestDto;
import com.example.board.security.LoginUser;
import com.example.board.security.dto.SessionUser;
import com.example.board.service.OpinionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
public class OpinionApiController {

    private final OpinionService opinionService;

    @PostMapping("v1/opinion")
    public OpinionResponseDto create(@RequestBody OpinionCreateRequestDto opinionCreateRequestDto) {
        return opinionService.create(opinionCreateRequestDto);
    }//create() end

    @GetMapping("v1/opinion/{id}")
    public OpinionResponseDto read(@PathVariable Long id) {
        return opinionService.read(id);
    }//read() end

    @PutMapping("v1/opinion")
    public Long update(@RequestBody OpinionUpdateRequestDto opinionUpdateRequestDto) {
        return opinionService.update(opinionUpdateRequestDto);
    }//update() end

    @PutMapping("v1/opinion/{id}/like")
    public int like(@LoginUser SessionUser user, @PathVariable Long id) {
        return opinionService.like(id, user.getId().toString());
    }//like() end

    @PutMapping("v1/opinion/{id}/dislike")
    public int dislike(@LoginUser SessionUser user, @PathVariable Long id) {
        return opinionService.dislike(id, user.getId().toString());
    }//dislike() end


    @DeleteMapping("v1/opinion/{id}")
    public Long delete(@PathVariable Long id) {
        return opinionService.delete(id);
    }//delete() end

}
