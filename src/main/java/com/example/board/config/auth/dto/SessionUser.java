package com.example.board.config.auth.dto;

import com.example.board.model.entity.Users;
import com.example.board.model.enumclass.UserRole;
import com.example.board.model.network.dto.notification.NotificationListResponseDto;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SessionUser implements Serializable{

    private Long id;
    private String name;

    private UserRole role;

    private String email;

    private String picture;

    public SessionUser(Users users) {
        this.id = users.getId();
        this.name = users.getName();
        this.role = users.getRole();
        this.email = users.getEmail();
        this.picture = users.getPicture();
    }
}
