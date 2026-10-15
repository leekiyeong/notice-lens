package com.kiyoung.notice_lens.domain.notice.dto;

import com.kiyoung.notice_lens.domain.notice.entity.Notice;
import com.kiyoung.notice_lens.domain.notice.entity.NoticeStatus;

public record NoticeExtractResponse(
        Long id,
        NoticeStatus status,
        int textLength,
        String extractedText
) {
    public static NoticeExtractResponse from(Notice notice) {
        String text = notice.getExtractedText();
        return new NoticeExtractResponse(
                notice.getId(),
                notice.getStatus(),
                text == null ? 0 : text.length(),
                text
        );
    }
}
