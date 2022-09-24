package com.example.board.model.network.dto.post;

import com.example.board.model.entity.Category;
import com.example.board.model.entity.Post;
import com.example.board.model.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PostCreateRequestDto {
    private String title;

    private String content;

    private Category category;

    private Users users;

    @Builder
    public PostCreateRequestDto(String title, String content, Category category, Users users) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.users = users;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .users(users)
                .build();
    }

}
