package com.example.board.model.enumclass;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    ADMIN(0,"ROLE_ADMIN", "관리자"),
    GUEST(1,"ROLE_GUEST", "미인증 유저"),
    USER(2, "ROLE_USER", "인증완료 유저")
    ;

    private final Integer id;
    private final String key;
    private final String description;
}
