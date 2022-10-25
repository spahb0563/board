package com.example.board.model.network.dto.post;

import com.example.board.model.Time;
import com.example.board.model.entity.Post;
import com.example.board.model.enumclass.CategoryType;
import com.example.board.model.network.dto.category.CategoryMapping;
import com.example.board.model.network.dto.users.UsersResponseDto;
import lombok.Getter;

@Getter
public class PostListResponseDto {

    private Long id;

    private String title;

    private int viewCount;

    private int opinionCount;

    private String createdAt;

    private UsersResponseDto users;

    private CategoryType categoryType;

    public PostListResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.viewCount = entity.getViewCount();
        this.opinionCount = entity.getOpinionCount();
        this.createdAt = Time.setTime(entity.getCreatedAt());
        this.users = new UsersResponseDto(entity.getUsers());
        this.categoryType = entity.getCategory().getType();
    }

    public PostListResponseDto(CategoryMapping categoryMapping) {
        this.id = categoryMapping.getPostId();
        this.title = categoryMapping.getPostTitle();
        this.opinionCount = categoryMapping.getPostOpinionCount();
        this.viewCount = categoryMapping.getPostViewCount();
        this.createdAt = Time.setTime(categoryMapping.getPostCreatedAt());
        this.users = UsersResponseDto.builder()
                .nickname(categoryMapping.getUsersNickname())
                .picture(categoryMapping.getUsersPicture())
                .build();
    }
}
