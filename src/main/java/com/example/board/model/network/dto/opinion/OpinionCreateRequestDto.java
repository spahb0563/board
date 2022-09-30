package com.example.board.model.network.dto.opinion;

import com.example.board.model.entity.Opinion;
import com.example.board.model.entity.Post;
import com.example.board.model.entity.Users;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public class OpinionCreateRequestDto {

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    private Long postId;

    private Long usersId;

    private Long parentOpinionId;

    public Opinion toEntity(Post post, Users users, Opinion opinion) {
        return Opinion.builder()
                .content(content)
                .post(post)
                .users(users)
                .opinion(opinion)
                .build();
    }
}
