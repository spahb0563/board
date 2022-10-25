package com.example.board.repository;

import com.example.board.model.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByReceiverIdAndId(Long userId, Long id);

    Page<Notification> findAllByReceiverId(Long userId, Pageable pageable);

    @Modifying
    @Query("update Notification n set n.readAt = current_time where n.receiver.id = :userId and n.readAt is null")
    int bulkUpdate(@Param("userId") Long userId);

    int deleteAllByReceiverIdAndReadAtIsNotNull(Long userId);

    boolean existsByReceiverIdAndReadAtIsNull(Long id);

    List<Notification> findAllByReceiverIdOrderByIdDesc(Long id);

}
