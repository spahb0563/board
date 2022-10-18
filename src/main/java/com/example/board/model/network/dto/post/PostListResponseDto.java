package com.example.board.model.network.dto.post;

import com.example.board.model.entity.Post;
import com.example.board.model.network.dto.category.CategoryMapping;
import com.example.board.model.network.dto.users.UsersResponseDto;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Getter
public class PostListResponseDto {

    private Long id;

    private String title;

    private int viewCount;

    private int opinionCount;

    private String createdAt;

    private UsersResponseDto users;

    private String categoryTitle;

    public PostListResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.viewCount = entity.getViewCount();
        this.opinionCount = entity.getOpinionCount();
        this.createdAt = setTime(entity.getCreatedAt());
        this.users = new UsersResponseDto(entity.getUsers());
        this.categoryTitle = entity.getCategory().getType().getTitle();
    }

    public PostListResponseDto(CategoryMapping categoryMapping) {
        this.id = categoryMapping.getPostId();
        this.title = categoryMapping.getPostTitle();
        this.opinionCount = categoryMapping.getPostOpinionCount();
        this.viewCount = categoryMapping.getPostViewCount();
        this.createdAt = setTime(categoryMapping.getPostCreatedAt());
        this.users = UsersResponseDto.builder()
                .nickname(categoryMapping.getUsersNickname())
                .picture(categoryMapping.getUsersPicture())
                .build();
    }

    public String setTime(LocalDateTime time) {
        LocalDate now = LocalDate.now();
        if(now.isEqual(time.toLocalDate())) {
            return DateTimeFormatter.ofPattern("HH:mm").format(time);
        }else {
            return time.format(DateTimeFormatter.ofPattern("yy. M. d HH:mm"));
        }
    }
}
