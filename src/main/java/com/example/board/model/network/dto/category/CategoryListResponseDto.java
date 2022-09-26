package com.example.board.model.network.dto.category;

import com.example.board.model.entity.Category;
import com.example.board.model.entity.Post;
import com.example.board.model.enumclass.CategoryType;
import com.example.board.model.network.dto.post.PostListResponseDto;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoryListResponseDto {

    private Long id;

    private CategoryType categoryType;

    private List<PostListResponseDto> postList;

    public CategoryListResponseDto(Category entity) {
        this.id = entity.getId();
        this.categoryType = entity.getType();
        this.postList = entity.getPostList().stream()
                .limit(5)
                .map(post -> new PostListResponseDto(post))
                .collect(Collectors.toList());
    }
}
