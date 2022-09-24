package com.example.board.model.network;

import lombok.Getter;

import java.util.List;

@Getter
public class PaginationDto<T> {

    private List<T> dto;

    private Pagination pagination;

    public PaginationDto (List<T> dto, Pagination pagination) {
        this.dto = dto;
        this.pagination = pagination;
    }
}
