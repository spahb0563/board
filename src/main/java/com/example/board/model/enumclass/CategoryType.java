package com.example.board.model.enumclass;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CategoryType {

    FREE(0,"자유", "자유 게시판"),
    HUMOR(1,"유머", "유머 게시판"),
    GAME(2,"게임", "게임 게시판"),
    SPORT( 3,"스포츠", "스포츠 게시판"),
    IT( 4,"IT", "IT 게시판"),
    QUESTION( 5,"질문", "질문 게시판");

    private final Integer Id;

    private final String title;

    private final String description;

    public static boolean contains(String type) {
        for(CategoryType c : CategoryType.values()) {
            if(c.name().equals(type)){
                return true;
            }
        }
        return false;
    }




}
