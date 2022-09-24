package com.example.board.model.network.dto.opinion;

import com.example.board.model.entity.Opinion;

import java.util.List;
import java.util.stream.Collectors;

public class OpinionDto {

    private String content;

    private List<OpinionDto> childOpinionList;

    public OpinionDto(Opinion entity) {
        this.content = entity.getContent();
        this.childOpinionList = entity.getChildOpinionList().stream()
                .map(opinion -> new OpinionDto(opinion)).collect(Collectors.toList());
    }
}
