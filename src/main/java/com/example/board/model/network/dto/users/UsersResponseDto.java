package com.example.board.model.network.dto.users;

import com.example.board.model.entity.Users;
import lombok.Getter;

@Getter
public class UsersResponseDto {

    private String nickname;

    private String picture;


    public UsersResponseDto(Users entity) {
        this.nickname = entity.getNickname();
        this.picture = entity.getPicture();
    }

    public UsersResponseDto(String nickname, String picture) {
        this.nickname = nickname;
        this.picture = picture;
    }
}
