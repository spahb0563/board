package com.example.board.service;


import com.example.board.model.entity.Notification;
import com.example.board.model.network.dto.notification.NotificationCreateRequestDto;
import com.example.board.model.network.dto.notification.NotificationListResponseDto;
import com.example.board.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void create(NotificationCreateRequestDto notificationCreateRequestDto) {
        notificationRepository.save(notificationCreateRequestDto.toEntity());
    }//create() end

    @Transactional(readOnly = true)
    public List<NotificationListResponseDto> readAll(Long userId) {
        notificationRepository.bulkUpdate(userId);

        return notificationRepository.findAllByReceiverIdOrderByIdDesc(userId)
                .stream().map(notification -> new NotificationListResponseDto(notification))
                .collect(Collectors.toList());
    }//readAll() end

    public boolean checkNew(Long userId) {
        return notificationRepository.existsByReceiverIdAndReadAtIsNull(userId);
    }//checkNew() end

    @Transactional
    public Long delete(Long userId, Long id) {

        Notification notification = notificationRepository
                .findByReceiverIdAndId(userId, id).orElseThrow(() -> new IllegalArgumentException("해당 알림이 없습니다.  : " + id));

        notificationRepository.delete(notification);

        return id;
    }//delete() End

    @Transactional
    public int deleteAll(Long userId) {
        return notificationRepository.deleteAllByReceiverIdAndReadAtIsNotNull(userId);
    }//deleteAll() end
}
