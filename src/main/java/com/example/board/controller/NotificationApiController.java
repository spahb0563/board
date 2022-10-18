package com.example.board.controller;

import com.example.board.model.network.dto.notification.NotificationListResponseDto;
import com.example.board.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
public class NotificationApiController {

    private final NotificationService notificationService;

    @GetMapping("v1/notification/{userId}")
    public List<NotificationListResponseDto> readAll(@PathVariable Long userId) {
        return notificationService.readAll(userId);
    }

    @GetMapping("v1/notification/{userId}/new")
    public boolean checkNew(@PathVariable Long userId) {
        return notificationService.checkNew(userId);
    }

    @DeleteMapping("v1/notification/{userId}/{id}")
    public Long delete(@PathVariable Long userId, @PathVariable Long id) {
        return notificationService.delete(userId, id);
    }

    @DeleteMapping("v1/notification/{userId}")
    public int deleteAll(@PathVariable Long userId) {
        return notificationService.deleteAll(userId);
    }
}
