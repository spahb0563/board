package com.example.board.model.network.dto.post;

import com.example.board.model.entity.Post;
import com.example.board.model.network.dto.opinion.OpinionDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {

    private String title;

    private String content;

    private int viewCount;

    private int voteCount;

    private int opinionCount;

    private List<OpinionDto> opinionList;

    public PostResponseDto(Post entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.viewCount = entity.getViewCount();
        this.voteCount = entity.getVoteCount();
        this.opinionCount = entity.getOpinionCount();
        this.opinionList = entity.getOpinionList().stream()
                .map(opinion -> new OpinionDto(opinion)).collect(Collectors.toList());
    }
}
