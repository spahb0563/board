package com.example.board.model.network;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Pagination {

    private Integer totalPages;

    private Long totalElements;

    private Integer currentPage;

    private Integer currentElements;
}