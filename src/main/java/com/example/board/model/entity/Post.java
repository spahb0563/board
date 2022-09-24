package com.example.board.model.entity;


import com.example.board.model.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Mysql 전용 전략
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int voteCount;

    @Column(nullable = false)
    private int opinionCount;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Users users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "content")
    private List<Opinion> opinionList;

    @Builder
    public Post(String title, String content, Category category, Users users) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.users = users;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
