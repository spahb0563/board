package com.example.board.model.network.dto.post;

import com.example.board.model.entity.Post;
import com.example.board.model.network.dto.opinion.OpinionResponseDto;
import com.example.board.model.network.dto.users.UsersResponseDto;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;

    private String content;

    private int viewCount;

    private int likeCount;

    private int dislikeCount;

    private int opinionCount;

    private String createdAt;

    private UsersResponseDto users;
    private List<OpinionResponseDto> opinionList;


    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.users = new UsersResponseDto(entity.getUsers());
        this.createdAt = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yy. M. d HH:mm"));
        this.viewCount = entity.getViewCount();
        this.likeCount = entity.getLikeCount();
        this.dislikeCount = entity.getDislikeCount();
        this.opinionCount = entity.getOpinionCount();
        this.opinionList = entity.getOpinionList().stream().filter(opinion -> opinion.getParentOpinion() == null)
                .map(opinion -> new OpinionResponseDto(opinion)).collect(Collectors.toList());
    }
}
