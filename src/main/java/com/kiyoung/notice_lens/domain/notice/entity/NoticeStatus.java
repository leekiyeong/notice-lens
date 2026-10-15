package com.kiyoung.notice_lens.domain.notice.entity;

public enum NoticeStatus {
    UPLOADED,    // 업로드 완료
    EXTRACTED,   // 텍스트 추출 완료
    ANALYZED,    // AI 분석 완료
    FAILED       // 처리 실패
}