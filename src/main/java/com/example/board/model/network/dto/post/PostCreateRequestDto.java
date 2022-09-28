package com.example.board.model.network.dto.post;

import com.example.board.model.entity.Category;
import com.example.board.model.entity.Post;
import com.example.board.model.entity.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreateRequestDto {
    private String title;

    private String content;

    private String category;

    private Long usersId;

    @Builder
    public PostCreateRequestDto(String title, String content, String category, Long usersId) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.usersId = usersId;
    }

    public Post toEntity(Category category, Users users) {
        return Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .users(users)
                .build();
    }
}
