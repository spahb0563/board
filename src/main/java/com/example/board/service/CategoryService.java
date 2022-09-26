package com.example.board.service;

import com.example.board.model.network.dto.category.CategoryListResponseDto;
import com.example.board.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true) // 메인 화면에 출력될 카테고리별 최신글
    public List<CategoryListResponseDto> findAllCategoryRecentPost() {
        return categoryRepository.findAllJoinFetch().stream()
                .map(category -> new CategoryListResponseDto(category))
                .collect(Collectors.toList())
                ;
    }// findAllCategoryRecentPost() end
}
