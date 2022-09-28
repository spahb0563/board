package com.example.board.repository;


import com.example.board.config.JpaConfig;
import com.example.board.model.entity.Category;
import com.example.board.model.enumclass.CategoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = JpaConfig.class))
@DisplayName("CategoryRepository 테스트")
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void saveAndRead() {
        //given
        CategoryType type = CategoryType.FREE;
        String title = "자유게시판";

        categoryRepository.save(new Category(type));
        //when
        List<Category> categoryList = categoryRepository.findAll();
        //then
        Category category = categoryList.get(0);
        assertThat(category.getType()).isEqualTo(type);
    }//saveAndRead() end

    @Test
    public void saveAndDelete() {
        //given
        CategoryType type = CategoryType.FREE;
        String title = "자유게시판";

        categoryRepository.save(new Category(type));
        //when
        categoryRepository.deleteAll();
        //then
        assertThat(categoryRepository.findAll()).isEmpty();
    }//saveAndDelete() end

    @Test
    public void findAll() {
        categoryRepository.findTopFivePostOfAllCategory().stream().forEach(categoryMapping -> {
            System.out.println(categoryMapping.getCategoryType());
            System.out.println(categoryMapping.getPostId());
        });
    }
}
