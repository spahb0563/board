package com.example.board.model.network.dto.post;

import com.example.board.model.entity.Post;
import com.example.board.model.network.dto.opinion.OpinionDto;
import com.example.board.model.network.dto.users.UsersDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {

    private String title;

    private String content;

    private int viewCount;

    private int voteCount;

    private int opinionCount;

    private String createdAt;

    private UsersDto users;
    private List<OpinionDto> opinionList;


    public PostResponseDto(Post entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.users = new UsersDto(entity.getUsers());
        this.createdAt = entity.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        this.viewCount = entity.getViewCount();
        this.voteCount = entity.getVoteCount();
        this.opinionCount = entity.getOpinionCount();
        this.opinionList = entity.getOpinionList().stream()
                .map(opinion -> new OpinionDto(opinion)).collect(Collectors.toList());

    }
}
