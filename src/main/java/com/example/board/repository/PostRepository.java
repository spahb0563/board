package com.example.board.repository;

import com.example.board.model.entity.Post;
import com.example.board.model.enumclass.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select distinct p from Post p join p.users u on p.id = u.id where p.category.type = :categoryType")
    Page<Post> findAllByCategoryType(CategoryType categoryType, Pageable pageable);

    @Modifying
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :id")
    int updateView(Long id);

    @Query("select p from Post p where p.category.type = :categoryType")
    List<Post> findTopFiveByCategoryType(CategoryType categoryType);

}
