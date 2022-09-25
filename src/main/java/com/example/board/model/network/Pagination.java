package com.example.board.model.network;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Builder
public class Pagination {

    private int totalPages; // 전체 페이지수

    private long totalElements; // 전체 데이터수

    private int currentPage; // 현재 페이지수

    private int currentElements; // 현재 데이터수

}