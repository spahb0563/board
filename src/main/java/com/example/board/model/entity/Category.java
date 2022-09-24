package com.example.board.model.entity;

import com.example.board.model.enumclass.CategoryType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Post> postList;

    @CreatedDate
    private LocalDateTime created_at;

    @Builder
    public Category(CategoryType type) {
        this.type = type;
    }
}
