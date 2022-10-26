package com.example.board.model.entity;

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
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String savedName;

    private String path;

    private String type;

    private Long size;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public File(String name, String savedName, String path, String type, Long size) {
        this.name = name;
        this.savedName = savedName;
        this.path = path;
        this.type = type;
        this.size = size;
    }


}
