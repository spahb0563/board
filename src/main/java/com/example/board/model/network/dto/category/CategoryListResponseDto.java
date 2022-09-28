package com.example.board.model.network.dto.category;

import com.example.board.model.entity.Category;
import com.example.board.model.enumclass.CategoryType;
import com.example.board.model.network.dto.post.CategoryMapping;
import com.example.board.model.network.dto.post.PostListResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryListResponseDto {

    private Long id;

    private CategoryType categoryType;

    private List<PostListResponseDto> postList;

    public CategoryListResponseDto(Category entity) {
        this.id = entity.getId();
        this.categoryType = entity.getType();
    }

    public CategoryListResponseDto(Long id, CategoryType categoryType, List<PostListResponseDto> postList) {
        this.id = id;
        this.categoryType = categoryType;
        this.postList = postList;
    }


}
