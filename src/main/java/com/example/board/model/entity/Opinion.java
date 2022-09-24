package com.example.board.model.entity;


import com.example.board.model.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Opinion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Users users;

    @ManyToOne
    private Opinion parentOpinion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentOpinion")
    private List<Opinion> childOpinionList;

    @Builder
    public Opinion(String content, Post post, Users users, Opinion opinion) {
        this.content = content;
        this.post = post;
        this.users = users;
        this.parentOpinion = opinion;
    }

    public void update(String content) {
        this.content = content;
    }
}


