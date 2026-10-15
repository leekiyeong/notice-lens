package com.kiyoung.notice_lens.domain.notice.dto;

import com.kiyoung.notice_lens.domain.notice.entity.Notice;
import com.kiyoung.notice_lens.domain.notice.entity.NoticeStatus;

public record NoticeUploadResponse(
        Long id,
        String title,
        String originalFileName,
        NoticeStatus status
) {
    public static NoticeUploadResponse from(Notice notice) {
        return new NoticeUploadResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getOriginalFileName(),
                notice.getStatus()
        );
    }
}