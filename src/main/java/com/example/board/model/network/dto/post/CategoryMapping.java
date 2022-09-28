package com.example.board.model.network.dto.post;

import com.example.board.model.enumclass.CategoryType;

import java.time.LocalDateTime;

public interface CategoryMapping {

    Long getCategoryId();
    CategoryType getCategoryType();
    Long getPostId();
    String getPostTitle();
    int getPostViewCount();
    int getPostVoteCount();
    int getPostOpinionCount();
    LocalDateTime getPostCreatedAt();

    String getUsersNickname();
    String getUsersPicture();
}
