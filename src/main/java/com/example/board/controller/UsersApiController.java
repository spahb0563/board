package com.example.board.controller;

import com.example.board.model.network.dto.post.PostCreateRequestDto;
import com.example.board.model.network.dto.post.PostResponseDto;
import com.example.board.model.network.dto.post.PostUpdateRequestDto;
import com.example.board.model.network.dto.users.UsersUpdateRequestDto;
import com.example.board.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UsersApiController {

    private final UsersService usersService;

//    @PostMapping("v1/Users")
//    public Long create() {
//        return postService.create(postCreateRequestDto);
//    }//create() end

    @PutMapping("v1/users")
    public Long update(@RequestBody UsersUpdateRequestDto usersUpdateRequestDto) {
        return usersService.update(usersUpdateRequestDto);
    }//update() end


}
