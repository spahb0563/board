package com.example.board.service;


import com.example.board.model.entity.Notification;
import com.example.board.model.entity.Post;
import com.example.board.model.network.Pagination;
import com.example.board.model.network.PaginationDto;
import com.example.board.model.network.dto.notification.NotificationCreateRequestDto;
import com.example.board.model.network.dto.notification.NotificationListResponseDto;
import com.example.board.model.network.dto.post.PostListResponseDto;
import com.example.board.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional
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

    @Transactional(readOnly = true)
    public PaginationDto readAllByUserId(Long id, Pageable pageable) {
        Page<Notification> notificationList = notificationRepository.findAllByReceiverId(id, pageable);

        return pagination(notificationList);
    }//readAllByUserId() end

    private PaginationDto pagination(Page<Notification> notificationsList) {
        List<NotificationListResponseDto> notificationListResponseDto = notificationsList.stream()
                .map(notification -> new NotificationListResponseDto(notification))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(notificationsList.getTotalPages())
                .totalElements(notificationsList.getTotalElements())
                .currentPage(notificationsList.getNumber())
                .currentElements(notificationsList.getNumberOfElements())
                .build();
        return new PaginationDto<List<NotificationListResponseDto>>(notificationListResponseDto, pagination);
    }// pagination() end

}
