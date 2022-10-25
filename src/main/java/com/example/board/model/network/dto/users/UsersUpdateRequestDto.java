package com.example.board.model.network.dto.users;

import lombok.Getter;

@Getter
public class UsersUpdateRequestDto {

    private Long id;

    private String picture;

    private String name;

    private String nickname;
}
