package com.example.board.model.network.dto.post;

import com.example.board.model.entity.Post;
import com.example.board.model.network.dto.users.UsersDto;
import lombok.Getter;

@Getter
public class PostListResponseDto {

    private Long id;

    private String title;

    private int viewCount;

    private int voteCount;

    private int opinionCount;

    private UsersDto users;

    public PostListResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.viewCount = entity.getViewCount();
        this.voteCount = entity.getVoteCount();
        this.opinionCount = entity.getOpinionCount();
        this.users = new UsersDto(entity.getUsers());
    }
}
