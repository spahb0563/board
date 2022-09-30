package com.example.board.model.entity;


import com.example.board.model.BaseTimeEntity;
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
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private int dislikeCount;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Users users;

    @ManyToOne
    private Opinion parentOpinion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentOpinion")
    private List<Opinion> childOpinionList;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;

    @Builder
    public Opinion(String content, Post post, Users users, Opinion opinion) {
        this.content = content;
        this.post = post;
        this.users = users;
        this.parentOpinion = opinion;
    }

    public void update(String content) {
        this.content = content;
        this.modifiedAt = LocalDateTime.now();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}


