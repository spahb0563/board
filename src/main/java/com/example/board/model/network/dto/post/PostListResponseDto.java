package com.example.board.model.network.dto.post;

import com.example.board.model.entity.Post;
import com.example.board.model.network.dto.category.CategoryMapping;
import com.example.board.model.network.dto.users.UsersResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {

    private Long id;

    private String title;

    private int viewCount;

    private int opinionCount;

    private LocalDateTime createdAt;

    private UsersResponseDto users;

    public PostListResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.viewCount = entity.getViewCount();
        this.opinionCount = entity.getOpinionCount();
        this.createdAt = entity.getCreatedAt();
        this.users = new UsersResponseDto(entity.getUsers());
    }

    public PostListResponseDto(CategoryMapping categoryMapping) {
        this.id = categoryMapping.getPostId();
        this.title = categoryMapping.getPostTitle();
        this.opinionCount = categoryMapping.getPostOpinionCount();
        this.viewCount = categoryMapping.getPostViewCount();
        this.createdAt = categoryMapping.getPostCreatedAt();
        this.users = new UsersResponseDto(categoryMapping.getUsersNickname(), categoryMapping.getUsersPicture());
    }
}
