package com.example.board.repository;

import com.example.board.model.entity.Post;
import com.example.board.model.enumclass.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndDeletedAtIsNull(Long id);

    Page<Post> findAllByCategoryTypeAndDeletedAtIsNull(CategoryType categoryType, Pageable pageable);

    Page<Post> findAllByCategoryTypeAndTitleContainsAndDeletedAtIsNull(CategoryType categoryType, String keyword, Pageable pageable);

    Page<Post> findAllByTitleContainsAndDeletedAtIsNull(String keyword, Pageable pageable);

}
