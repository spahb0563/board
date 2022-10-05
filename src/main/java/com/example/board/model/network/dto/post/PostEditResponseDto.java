package com.example.board.model.network.dto.post;

import com.example.board.model.entity.Post;
import lombok.Getter;

@Getter
public class PostEditResponseDto {

    private Long id;

    private String title;

    private String content;

    public PostEditResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
    }
}
