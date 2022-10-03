package com.example.board.config.auth.dto;

import com.example.board.model.entity.Users;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable{

    private Long id;
    private String name;

    private String email;

    private String picture;

    public SessionUser(Users users) {
        this.id = users.getId();
        this.name = users.getName();
        this.email = users.getEmail();
        this.picture = users.getPicture();
    }
}
