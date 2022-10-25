package com.example.board.repository;

import com.example.board.model.entity.Post;
import com.example.board.model.enumclass.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndDeletedAtIsNull(Long id);

    Page<Post> findAllByDeletedAtIsNull(Pageable pageable);

    Page<Post> findAllByCategoryTypeAndDeletedAtIsNull(CategoryType categoryType, Pageable pageable);

    Page<Post> findAllByUsersIdAndDeletedAtIsNull(Long userId, Pageable pageable);

    @Query(value = "SELECT p.* FROM post p left outer join category c on p.category_id = c.id WHERE c.type = ?1 AND MATCH(title) AGAINST (?2 IN BOOLEAN MODE) AND deleted_at IS NULL", nativeQuery = true)
    Page<Post> findAllByCategoryTypeAndKeyword(String categoryType, String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM post WHERE MATCH(title) AGAINST (?1 IN BOOLEAN MODE) AND deleted_at IS NULL", nativeQuery = true)
    Page<Post> findAllByKeyword(String keyword, Pageable pageable);

    List<Post> findTop40ByCreatedAtGreaterThanEqualAndDeletedAtIsNullOrderByLikeCountDescViewCountDesc(LocalDateTime localDateTime);
}
