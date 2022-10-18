package com.example.board.model.network.dto.notification;

import com.example.board.model.entity.Notification;
import com.example.board.model.entity.Post;
import com.example.board.model.entity.Users;
import com.example.board.model.enumclass.NotificationType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationCreateRequestDto {
    private Users sender;

    private Long targetId;

    private Users receiver;

    private Post post;

    private NotificationType type;

    @Builder
    public NotificationCreateRequestDto(Long targetId, Users sender, Users receiver, Post post, NotificationType type) {
        this.targetId = targetId;
        this.sender = sender;
        this.receiver = receiver;
        this.post = post;
        this.type = type;
    }

    public Notification toEntity() {
        return Notification.builder()
                .targetId(targetId)
                .post(post)
                .receiver(receiver)
                .sender(sender)
                .type(type)
                .build();
    }

}
