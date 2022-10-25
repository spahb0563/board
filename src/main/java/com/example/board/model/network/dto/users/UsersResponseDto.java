package com.example.board.model.network.dto.users;

import com.example.board.model.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
public class UsersResponseDto {

    private Long id;

    private String email;

    private String name;
    private String nickname;

    private String picture;

    public UsersResponseDto(Users entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.name = entity.getName();
        this.nickname = entity.getNickname();
        this.picture = entity.getPicture();
    }
}
