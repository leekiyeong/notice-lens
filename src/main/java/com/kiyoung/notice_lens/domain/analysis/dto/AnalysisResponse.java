package com.kiyoung.notice_lens.domain.analysis.dto;

import com.kiyoung.notice_lens.domain.analysis.entity.NoticeAnalysis;
import java.time.LocalDateTime;

public record AnalysisResponse(
        Long id,
        Long noticeId,
        String summary,
        String modelName,
        LocalDateTime createdAt
) {
    public static AnalysisResponse from(NoticeAnalysis analysis){
        return new AnalysisResponse(
                analysis.getId(),
                analysis.getNotice().getId(),
                analysis.getSummary(),
                analysis.getModelName(),
                analysis.getCreatedAt()
        );
    }
}
