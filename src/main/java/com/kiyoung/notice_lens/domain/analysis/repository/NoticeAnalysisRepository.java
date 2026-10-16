package com.kiyoung.notice_lens.domain.analysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kiyoung.notice_lens.domain.analysis.entity.NoticeAnalysis;

import java.util.List;

public interface NoticeAnalysisRepository extends JpaRepository<NoticeAnalysis,Long> {

    List<NoticeAnalysis> findAllByNoticeIdOrderByCreatedAtDesc(Long noticeId);
}
