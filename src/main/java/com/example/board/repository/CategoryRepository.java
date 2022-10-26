package com.example.board.repository;

import com.example.board.model.entity.Category;
import com.example.board.model.enumclass.CategoryType;
import com.example.board.model.network.dto.category.CategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    Optional<Category> findByType(CategoryType categoryType);

    @Query(value = "" +
            "SELECT c.id            categoryId,\n" +
            "       c.type          categoryType,\n" +
            "       p.id            postId,\n" +
            "       p.title         postTitle,\n" +
            "       p.view_count    postViewCount,\n" +
            "       p.opinion_count postOpinionCount,\n" +
            "       p.created_at    postCreatedAt,\n" +
            "       u.id            usersId,\n" +
            "       u.nickname      usersNickname,\n" +
            "       u.picture       usersPicture\n" +
            "FROM   category c\n" +
            "       LEFT JOIN (SELECT *\n" +
            "                  FROM   (SELECT id,\n" +
            "                                 title,\n" +
            "                                 view_count,\n" +
            "                                 opinion_count,\n" +
            "                                 created_at,\n" +
            "                                 category_id,\n" +
            "                                 users_id\n" +
            "                          FROM   post\n" +
            "                          WHERE  category_id = 1\n" +
            "                                 AND deleted_at IS NULL\n" +
            "                          ORDER  BY id DESC\n" +
            "                          LIMIT  5) p1\n" +
            "                  UNION ALL\n" +
            "                  SELECT *\n" +
            "                  FROM   (SELECT id,\n" +
            "                                 title,\n" +
            "                                 view_count,\n" +
            "                                 opinion_count,\n" +
            "                                 created_at,\n" +
            "                                 category_id,\n" +
            "                                 users_id\n" +
            "                          FROM   post\n" +
            "                          WHERE  category_id = 2\n" +
            "                                 AND deleted_at IS NULL\n" +
            "                          ORDER  BY id DESC\n" +
            "                          LIMIT  5) p2\n" +
            "                  UNION ALL\n" +
            "                  SELECT *\n" +
            "                  FROM   (SELECT id,\n" +
            "                                 title,\n" +
            "                                 view_count,\n" +
            "                                 opinion_count,\n" +
            "                                 created_at,\n" +
            "                                 category_id,\n" +
            "                                 users_id\n" +
            "                          FROM   post\n" +
            "                          WHERE  category_id = 3\n" +
            "                                 AND deleted_at IS NULL\n" +
            "                          ORDER  BY id DESC\n" +
            "                          LIMIT  5) p3\n" +
            "                  UNION ALL\n" +
            "                  SELECT *\n" +
            "                  FROM   (SELECT id,\n" +
            "                                 title,\n" +
            "                                 view_count,\n" +
            "                                 opinion_count,\n" +
            "                                 created_at,\n" +
            "                                 category_id,\n" +
            "                                 users_id\n" +
            "                          FROM   post\n" +
            "                          WHERE  category_id = 4\n" +
            "                                 AND deleted_at IS NULL\n" +
            "                          ORDER  BY id DESC\n" +
            "                          LIMIT  5) p4\n" +
            "                  UNION ALL\n" +
            "                  SELECT *\n" +
            "                  FROM   (SELECT id,\n" +
            "                                 title,\n" +
            "                                 view_count,\n" +
            "                                 opinion_count,\n" +
            "                                 created_at,\n" +
            "                                 category_id,\n" +
            "                                 users_id\n" +
            "                          FROM   post\n" +
            "                          WHERE  category_id = 5\n" +
            "                                 AND deleted_at IS NULL\n" +
            "                          ORDER  BY id DESC\n" +
            "                          LIMIT  5) p5\n" +
            "                  UNION ALL\n" +
            "                  SELECT *\n" +
            "                  FROM   (SELECT id,\n" +
            "                                 title,\n" +
            "                                 view_count,\n" +
            "                                 opinion_count,\n" +
            "                                 created_at,\n" +
            "                                 category_id,\n" +
            "                                 users_id\n" +
            "                          FROM   post\n" +
            "                          WHERE  category_id = 6\n" +
            "                                 AND deleted_at IS NULL\n" +
            "                          ORDER  BY id DESC\n" +
            "                          LIMIT  5) p6\n" +
            "                  UNION ALL\n" +
            "                  SELECT *\n" +
            "                  FROM   (SELECT id,\n" +
            "                                 title,\n" +
            "                                 view_count,\n" +
            "                                 opinion_count,\n" +
            "                                 created_at,\n" +
            "                                 category_id,\n" +
            "                                 users_id\n" +
            "                          FROM   post\n" +
            "                          WHERE  category_id = 7\n" +
            "                                 AND deleted_at IS NULL\n" +
            "                          ORDER  BY id DESC\n" +
            "                          LIMIT  5) p7) p\n" +
            "              ON c.id = p.category_id\n" +
            "       LEFT JOIN users u\n" +
            "         ON p.users_id = u.id order by c.id, p.id desc;\n", nativeQuery = true)
    List<CategoryMapping> findTopFivePostOfAllCategory();
}
