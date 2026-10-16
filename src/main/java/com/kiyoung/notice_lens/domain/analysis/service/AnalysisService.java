package com.kiyoung.notice_lens.domain.analysis.service;

import com.kiyoung.notice_lens.domain.analysis.dto.AnalysisResponse;
import com.kiyoung.notice_lens.domain.analysis.entity.NoticeAnalysis;
import com.kiyoung.notice_lens.domain.analysis.repository.NoticeAnalysisRepository;
import com.kiyoung.notice_lens.domain.notice.entity.Notice;
import com.kiyoung.notice_lens.domain.notice.repository.NoticeRepository;
import com.kiyoung.notice_lens.domain.notice.service.NoticeService;
import com.kiyoung.notice_lens.global.exception.BusinessException;
import com.kiyoung.notice_lens.global.exception.ErrorCode;
import com.kiyoung.notice_lens.infra.llm.LlmClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalysisService {

    private static final String SUMMARY_SYSTEM_PROMPT = """
            너는 공공기관 공고문을 분석하는 전문가다.
            사용자가 공고문 본문을 주면, 아래 형식의 한국어 Markdown으로 요약하라.

            ## 공고 개요
            ## 신청 자격
            ## 선정 기준
            ## 주요 일정
            ## 제출 서류
            ## 유의 사항
            ## 문의처
            ## 기타

            규칙:
            - 공고문에 적힌 내용만 사용하고, 없는 내용은 지어내지 마라.
            - 숫자, 날짜, 금액은 원문 그대로 옮겨라.
            - 원문의 표는 Markdown 표로 행과 열을 유지해서 옮겨라.
            - "※"로 시작하는 단서·예외 조항은 관련 항목 아래에 빠짐없이 포함하라.
            - 위 항목에 맞지 않는 정보는 버리지 말고 "기타"에 넣어라.
            - 어떤 항목의 내용이 공고문에 전혀 없을 때만 "공고문에 명시되지 않음"이라고 적어라.
            """;

    private final NoticeRepository noticeRepository;
    private final NoticeAnalysisRepository analysisRepository;
    private final LlmClient llmClient;

    @Transactional
    public AnalysisResponse summarize(Long noticeId) {
        // 공고문을 id로 먼저 찾아온다.
        Notice notice = noticeRepository.findByIdAndDeletedFalse(noticeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTICE_NOT_FOUND));

        // 해당 공고문의 추출문을 확인한다.
        if(notice.getExtractedText() == null || notice.getExtractedText().length() == 0) {
            throw new BusinessException(ErrorCode.NOTICE_NOT_EXTRACT);
        }

        String summary = llmClient.chat(SUMMARY_SYSTEM_PROMPT, notice.getExtractedText());
        NoticeAnalysis analysis = NoticeAnalysis.create(notice, summary, llmClient.modelName());
        analysisRepository.save(analysis);
        notice.completeAnalysis();

        return AnalysisResponse.from(analysis);
    }

    public List<AnalysisResponse> findAll(Long noticeId) {
        return analysisRepository.findAllByNoticeIdOrderByCreatedAtDesc(noticeId).stream()
                .map(AnalysisResponse::from)
                .toList();
    }
}
