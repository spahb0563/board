package com.example.board.service;

import com.example.board.model.entity.File;
import com.example.board.model.network.dto.file.FileResponseDto;
import com.example.board.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public Long fileUpload(MultipartFile file) throws IOException {

        String saveFileName = fileSave("/img", file);
        File saveFile = File.builder()
                .name(file.getOriginalFilename())
                .savedName(saveFileName)
                .type(file.getContentType())
                .size(file.getSize())
                .path("/img".replace(java.io.File.separatorChar, '/') + '/' + saveFileName)
                .build();

        return fileRepository.save(saveFile).getId();
    }//fileUpload() end

    public FileResponseDto read(Long id) {

        File file = fileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 파일입니다 fileId :" + id));

        return new FileResponseDto(file);
    }

    public String fileSave(String rootLocation, MultipartFile file) throws IOException {
        java.io.File uploadDir = new java.io.File(rootLocation);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // saveFileName 생성
        UUID uuid = UUID.randomUUID();
        String saveFileName = uuid + file.getOriginalFilename();
        java.io.File saveFile = new java.io.File(rootLocation, saveFileName);
        System.out.println(saveFile.getPath());
        FileCopyUtils.copy(file.getBytes(), saveFile);

        return saveFileName;
    }
}
