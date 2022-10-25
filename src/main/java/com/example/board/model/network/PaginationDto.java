package com.example.board.model.network;

import com.example.board.model.entity.Opinion;
import com.example.board.model.entity.Post;
import com.example.board.model.network.dto.post.PostListResponseDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PaginationDto<T> {

    private T data;

    private Pagination pagination;

    public PaginationDto (T data, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }
}
