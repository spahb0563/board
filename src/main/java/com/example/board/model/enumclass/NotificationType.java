package com.example.board.model.enumclass;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    OPINION("댓글", "님이 회원님이 작성하신 게시물에 댓글을 달았습니다."),
    REPLY("대댓글", "님이 회원님이 작성하신 댓글에 대댓글을 달았습니다.");

    private final String title;

    private final String message;

}
