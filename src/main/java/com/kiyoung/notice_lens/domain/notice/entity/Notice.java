package com.kiyoung.notice_lens.domain.notice.entity;

import com.kiyoung.notice_lens.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String originalFileName;   // 사용자가 올린 파일명

    @Column(nullable = false, unique = true)
    private String storedFileName;     // 서버에 저장한 파일명(중복 방지용)

    private Long fileSize;

    @Column(columnDefinition = "TEXT")
    private String extractedText;      // PDF에서 추출한 본문

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeStatus status;

    public static Notice create(String title, String originalFileName,
                                String storedFileName, Long fileSize) {
        Notice notice = new Notice();
        notice.title = title;
        notice.originalFileName = originalFileName;
        notice.storedFileName = storedFileName;
        notice.fileSize = fileSize;
        notice.status = NoticeStatus.UPLOADED;
        return notice;
    }

    public void completeExtraction(String extractedText) {
        this.extractedText = extractedText;
        this.status = NoticeStatus.EXTRACTED;
    }

    public void fail() {
        this.status = NoticeStatus.FAILED;
    }
}
