package com.example.board.repository;

import com.example.board.model.entity.Opinion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {

    Optional<Opinion> findAllByParentOpinionIsNull();

    Page<Opinion> findAllByUsersIdAndDeletedAtIsNull(Long userId, Pageable pageable);
}
