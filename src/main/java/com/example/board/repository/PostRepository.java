package com.example.board.repository;

import com.example.board.model.entity.Post;
import com.example.board.model.enumclass.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//    @Query("select p from Post p join p.category c on p.category.id = c.id join Users u on p.users.id = u.id where p.category.type = :categoryType")
    Page<Post> findAllByCategoryType(CategoryType categoryType, Pageable pageable);

}
