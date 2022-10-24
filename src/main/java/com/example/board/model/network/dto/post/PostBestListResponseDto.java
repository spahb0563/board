package com.example.board.model.network.dto.post;

import com.example.board.model.entity.Post;
import com.example.board.model.enumclass.CategoryType;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class PostBestListResponseDto implements Serializable {

    private Long id;

    private String title;

    private int likeCount;

    private String createdAt;

    private CategoryType categoryType;

    public PostBestListResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.likeCount = entity.getLikeCount();
        this.createdAt = setTime(entity.getCreatedAt());
        this.categoryType = entity.getCategory().getType();
    }

    public String setTime(LocalDateTime time) {
        LocalDate now = LocalDate.now();
        if(now.isEqual(time.toLocalDate())) {
            return DateTimeFormatter.ofPattern("HH:mm").format(time);
        }else {
            if(now.getYear() == time.getYear()) {
                return DateTimeFormatter.ofPattern("M. d HH:mm").format(time);
            }else {
                return DateTimeFormatter.ofPattern("yy. M. d HH:mm").format(time);
            }
        }
    }
}
