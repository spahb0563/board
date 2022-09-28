package com.example.board.model.network.dto.post;

import com.example.board.model.entity.Post;
import com.example.board.model.network.dto.users.UsersDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {

    private Long id;

    private String title;

    private int viewCount;

    private int voteCount;

    private int opinionCount;

    private LocalDateTime createdAt;

    private UsersDto users;

    public PostListResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.viewCount = entity.getViewCount();
        this.voteCount = entity.getVoteCount();
        this.opinionCount = entity.getOpinionCount();
        this.createdAt = entity.getCreatedAt();
        this.users = new UsersDto(entity.getUsers());
    }

    public PostListResponseDto(CategoryMapping categoryMapping) {
        this.id = categoryMapping.getPostId();
        this.title = categoryMapping.getPostTitle();
        this.viewCount = categoryMapping.getPostViewCount();
        this.voteCount = categoryMapping.getPostVoteCount();
        this.opinionCount = categoryMapping.getPostOpinionCount();
        this.createdAt = categoryMapping.getPostCreatedAt();
        this.users = new UsersDto(categoryMapping.getUsersNickname(), categoryMapping.getUsersPicture());
    }
}
