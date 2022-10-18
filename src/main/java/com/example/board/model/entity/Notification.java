package com.example.board.model.entity;

import com.example.board.model.enumclass.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long targetId;

    @ManyToOne
    private Users sender;

    @ManyToOne
    private Users receiver;

    @ManyToOne
    private Post post;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    @Builder
    public Notification(Long targetId, Users sender, Users receiver, Post post, NotificationType type) {
        this.targetId = targetId;
        this.sender = sender;
        this.receiver = receiver;
        this.post = post;
        this.type = type;
    }

    public void update() {
        readAt = LocalDateTime.now();
    }
}
