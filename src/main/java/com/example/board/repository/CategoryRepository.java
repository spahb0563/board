package com.example.board.repository;

import com.example.board.model.entity.Category;
import com.example.board.model.enumclass.CategoryType;
import com.example.board.model.network.dto.post.CategoryMapping;
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
    @Query(value = "SELECT c.id     categoryId,\n" +
            "       c.type          categoryType,\n" +
            "       p.id            postId,\n" +
            "       p.title         postTitle,\n" +
            "       p.view_count    postViewCount,\n" +
            "       p.vote_count    postVoteCount,\n" +
            "       p.opinion_count postOpinionCount,\n" +
            "       p.created_at    postCreatedAt,\n" +
            "       u.nickname      usersNickname,\n" +
            "       u.picture       usersPicture\n" +
            "FROM   category c\n" +
            "       LEFT JOIN (SELECT *\n" +
            "             FROM   (SELECT *,\n" +
            "                            Row_number()\n" +
            "                              OVER(\n" +
            "                                partition BY category_id\n" +
            "                                ORDER BY id DESC) rn\n" +
            "                     FROM   post) p0\n" +
            "             WHERE  p0.rn <= 5) p\n" +
            "         ON c.id = p.category_id\n" +
            "       LEFT JOIN users u\n" +
            "              ON p.users_id = u.id\n" +
            "ORDER  BY c.id,\n" +
            "          p.id DESC", nativeQuery = true)
    List<CategoryMapping> findTopFivePostOfAllCategory();
}
