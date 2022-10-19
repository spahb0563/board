package com.example.board.controller;

import com.example.board.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    private final ResourceLoader resourceLoader;

    @PostMapping("image")
    public String fileUpload(@RequestParam("file")MultipartFile file) throws IOException {

        String url = "/image/"+fileService.fileUpload(file);

        return url;
    }

    @GetMapping("image/{id}")
    public Resource read(@PathVariable Long id) {
        Resource resource = resourceLoader.getResource("file:"+fileService.read(id).getPath());

        return resource;
    }
}
