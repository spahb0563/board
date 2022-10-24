package com.example.board.model.network.dto.users;

import com.example.board.model.entity.Users;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UsersResponseDto {


    private Long id;
    private String nickname;

    private String picture;


    public UsersResponseDto(Users entity) {
        this.id = entity.getId();
        this.nickname = entity.getNickname();
        this.picture = entity.getPicture();
    }

    @Builder
    public UsersResponseDto(Long id, String nickname, String picture) {
        this.id = id;
        this.nickname = nickname;
        this.picture = picture;
    }
}
