package com.kiyoung.notice_lens.domain.notice.repository;

import com.kiyoung.notice_lens.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findAllByDeletedFalseOrderByCreatedAtDesc();
    Optional<Notice> findByIdAndDeletedFalse(Long id);
}