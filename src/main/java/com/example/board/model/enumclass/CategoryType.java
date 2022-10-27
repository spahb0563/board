package com.example.board.model.enumclass;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CategoryType {

    FREE(1,"자유", "자유 게시판", "badge badge-pill badge-primary"),
    HUMOR(2,"유머", "유머 게시판", "badge badge-pill badge-warning"),
    GAME(3,"게임", "게임 게시판", "badge badge-pill badge-danger"),
    SPORT( 4,"스포츠", "스포츠 게시판", "badge badge-pill badge-success"),
    IT( 5,"IT", "IT 게시판", "badge badge-pill badge-secondary"),
    FOOD(6, "요리", "요리 게시판", "badge badge-pill badge-light"),
    QUESTION( 7,"질문", "질문 게시판", "badge badge-pill badge-info");

    private final Integer Id;

    private final String title;

    private final String description;

    private final String color;

    public static boolean contains(String type) {
        for(CategoryType c : CategoryType.values()) {
            if(c.name().equals(type)){
                return true;
            }
        }
        return false;
    }
}
