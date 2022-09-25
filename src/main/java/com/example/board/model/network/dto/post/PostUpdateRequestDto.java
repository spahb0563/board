package com.example.board.model.network.dto.post;

import lombok.Getter;

@Getter
public class PostUpdateRequestDto {

    private Long id;

    private String title;

    private String content;

}
