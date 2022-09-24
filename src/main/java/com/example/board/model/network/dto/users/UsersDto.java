package com.example.board.model.network.dto.users;

import com.example.board.model.entity.Users;
import lombok.Getter;

@Getter
public class UsersDto {

    private String nickname;

    private String picture;


    public UsersDto(Users entity) {
        this.nickname = entity.getNickname();
        this.picture = entity.getPicture();
    }
}
