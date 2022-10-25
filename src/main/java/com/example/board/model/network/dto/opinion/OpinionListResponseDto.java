package com.example.board.model.network.dto.opinion;

import com.example.board.model.Time;
import com.example.board.model.entity.Opinion;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Getter
public class OpinionListResponseDto {

    private Long id;

    private Long postId;

    private String postTitle;

    private String content;

    private String createdAt;

    public OpinionListResponseDto(Opinion entity) {
        this.id = entity.getId();
        this.postTitle = entity.getPost().getTitle();
        this.postId = entity.getPost().getId();
        this.content = setContent(entity.getContent());
        this.createdAt = Time.setTime(entity.getCreatedAt());
    }

    private String setContent(String content) {
        Document doc = Jsoup.parse(content);
        doc.getElementsByTag("img").forEach(element -> element.appendText("[이미지]"));
        doc.getElementsByTag("iframe").forEach(element -> element.appendText("[동영상]"));
        return Jsoup.parse(doc.toString()).text();
    }
}
