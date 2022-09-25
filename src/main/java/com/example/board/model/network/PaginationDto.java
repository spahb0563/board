package com.example.board.model.network;

import lombok.Getter;

import java.util.List;

@Getter
public class PaginationDto<T> {

    private T data;

    private Pagination pagination;

    public PaginationDto (T data, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }
}
