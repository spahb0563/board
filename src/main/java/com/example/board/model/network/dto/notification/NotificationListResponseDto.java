package com.example.board.model.network.dto.notification;

import com.example.board.model.Time;
import com.example.board.model.entity.Notification;
import com.example.board.model.enumclass.NotificationType;
import com.example.board.model.network.dto.users.UsersResponseDto;
import lombok.Getter;

@Getter
public class NotificationListResponseDto {

    private Long id;

    private UsersResponseDto sender;

    private Long postId;

    private Long targetId;

    private NotificationType type;

    private String message;

    private String createdAt;

    public NotificationListResponseDto(Notification entity) {
        this.id = entity.getId();
        this.sender = new UsersResponseDto(entity.getSender());
        this.targetId = entity.getTargetId();
        this.postId = entity.getPost().getId();
        this.type = entity.getType();
        this.message = entity.getType().getMessage();
        this.createdAt = Time.convertLocaldatetimeToTime(entity.getCreatedAt());
    }
}
