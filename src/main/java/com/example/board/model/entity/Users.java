package com.example.board.model.entity;


import com.example.board.model.BaseTimeEntity;
import com.example.board.model.enumclass.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private List<Post> postList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private List<Opinion> opinionList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<Notification> sentNotificationList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver")
    private List<Notification> receivedNotificationList;

    @Builder
    public Users(String name, String email, String nickname, String picture, UserRole role) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.picture = picture;
        this.role = role;
    }
    public Users update(String name, String nickname, String picture) {
        this.name = name;
        this.nickname = nickname;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
