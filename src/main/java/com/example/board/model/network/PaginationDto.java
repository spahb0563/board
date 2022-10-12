package com.example.board.model.network;

import com.example.board.model.entity.Post;
import com.example.board.model.network.dto.post.PostListResponseDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PaginationDto {

    private List<PostListResponseDto> postListResponseDto;

    private Pagination pagination;

    public PaginationDto (Page<Post> postList) {
        this.postListResponseDto = postList.stream()
                .map(post -> new PostListResponseDto(post))
                .collect(Collectors.toList());
        this.pagination = Pagination.builder() //페이징 처리
                .totalPages(postList.getTotalPages())  // 전체 페이지 수
                .totalElements(postList.getTotalElements())  // 전체 요소수
                .currentPage(postList.getNumber()) // 현재 페이지
                .currentElements(postList.getNumberOfElements()) // 현재 요소의 순서
                .build();
    }
}
