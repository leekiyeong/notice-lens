package com.kiyoung.notice_lens.domain.notice.dto;

import com.kiyoung.notice_lens.domain.notice.entity.Notice;
import com.kiyoung.notice_lens.domain.notice.entity.NoticeStatus;

import java.time.LocalDateTime;

public record NoticeResponse(
        Long id,
        String title,
        String originalFileName,
        Long fileSize,
        NoticeStatus status,
        LocalDateTime createdAt
) {
    public static NoticeResponse from(Notice notice) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getOriginalFileName(),
                notice.getFileSize(),
                notice.getStatus(),
                notice.getCreatedAt()
        );
    }
}
