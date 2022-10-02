package com.example.board.service;

import com.example.board.model.network.dto.category.CategoryListResponseDto;
import com.example.board.model.network.dto.category.CategoryMapping;
import com.example.board.model.network.dto.post.PostListResponseDto;
import com.example.board.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true) // 메인 화면에 출력될 카테고리별 최신글
    public List<CategoryListResponseDto> findAllCategoryRecentPost() {
        List<CategoryMapping> mappingList = categoryRepository.findTopFivePostOfAllCategory();
        List<CategoryListResponseDto> categoryListResponseDto = new ArrayList();
        List<PostListResponseDto> postListResponseDto = new ArrayList();

        for (int i = 0; i < mappingList.size(); i++) {
            if (i == mappingList.size() - 1 ||
                    !mappingList.get(i).getCategoryId().equals(mappingList.get(i + 1).getCategoryId())) {
                if (mappingList.get(i).getPostId() != null) {
                    postListResponseDto.add(new PostListResponseDto(mappingList.get(i)));
                }
                categoryListResponseDto.add(new CategoryListResponseDto(mappingList.get(i).getPostId(), mappingList.get(i).getCategoryType(), postListResponseDto));

                postListResponseDto = new ArrayList();
            } else {
                if (mappingList.get(i).getPostId() != null) {
                    postListResponseDto.add(new PostListResponseDto(mappingList.get(i)));
                }
            }
        }
        categoryListResponseDto.forEach(categoryListResponseDto1 -> {
            System.out.println(categoryListResponseDto1.getCategoryType());
        });
        return categoryListResponseDto;
    }// findAllCategoryRecentPost() end

    public List<CategoryListResponseDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryListResponseDto(category))
                .collect(Collectors.toList());
    }
}
