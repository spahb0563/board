package com.example.board.repository;

import com.example.board.model.entity.Category;
import com.example.board.model.enumclass.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByType(CategoryType categoryType);

    @Query("select distinct c from Category c join fetch c.postList p join fetch p.users")
    List<Category> findAllJoinFetch();

}
