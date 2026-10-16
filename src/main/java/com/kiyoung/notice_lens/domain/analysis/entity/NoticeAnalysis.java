package com.kiyoung.notice_lens.domain.analysis.entity;

import com.kiyoung.notice_lens.domain.notice.entity.Notice;
import com.kiyoung.notice_lens.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeAnalysis extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String summary;

    @Column(nullable = false)
    private String modelName;

    public static NoticeAnalysis create(Notice notice, String summary, String modelName) {
        NoticeAnalysis analysis = new NoticeAnalysis();
        analysis.notice = notice;
        analysis.summary = summary;
        analysis.modelName = modelName;
        return analysis;
    }
}
