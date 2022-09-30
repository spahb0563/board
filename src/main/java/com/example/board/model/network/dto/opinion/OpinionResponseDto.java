package com.example.board.model.network.dto.opinion;

import com.example.board.model.entity.Opinion;
import com.example.board.model.network.dto.users.UsersResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpinionResponseDto {


    private Long id;
    private String content;

    private int likeCount;

    private int dislikeCount;

    private UsersResponseDto users;
    private List<OpinionResponseDto> childOpinionList;

    private String createdAt;

    public OpinionResponseDto(Opinion entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.likeCount = entity.getLikeCount();
        this.dislikeCount = entity.getDislikeCount();
        this.users = new UsersResponseDto(entity.getUsers());
        this.createdAt = entity.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        if(entity.getChildOpinionList() != null) this.childOpinionList = entity.getChildOpinionList().stream()
                .map(opinion -> new OpinionResponseDto(opinion)).collect(Collectors.toList());
    }
}
