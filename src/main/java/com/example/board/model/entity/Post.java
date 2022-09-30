package com.example.board.model.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Entity
public class Post {
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
    private int likeCount;

    @Column(nullable = false)
    private int dislikeCount;

    @Column(nullable = false)
    private int opinionCount;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Users users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Opinion> opinionList;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;

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
        this.modifiedAt = LocalDateTime.now();
    }

    public void plusViewCount() {
        this.viewCount++;
    }

    public void updateOpinionCount(int plusOrMinus) {this.opinionCount+=plusOrMinus;}

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
