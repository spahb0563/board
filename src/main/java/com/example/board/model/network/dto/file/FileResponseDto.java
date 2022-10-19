package com.example.board.model.network.dto.file;

import com.example.board.model.entity.File;
import lombok.Getter;

@Getter
public class FileResponseDto {

    private Long id;

    private String path;

    public FileResponseDto(File entity) {
        this.id = entity.getId();
        this.path = entity.getPath();
    }
}
