package com.kiyoung.notice_lens.domain.analysis.controller;

import com.kiyoung.notice_lens.domain.analysis.dto.AnalysisResponse;
import com.kiyoung.notice_lens.domain.analysis.service.AnalysisService;
import com.kiyoung.notice_lens.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices/{noticeId}/analyses")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    // AI 요약 실행
    @PostMapping
    public ResponseEntity<ApiResponse<AnalysisResponse>> summarize(@PathVariable Long noticeId) {
        AnalysisResponse response = analysisService.summarize(noticeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(response));
    }

    // 분석 이력 조회
    @GetMapping
    public ApiResponse<List<AnalysisResponse>> findAll(@PathVariable Long noticeId) {
        return ApiResponse.ok(analysisService.findAll(noticeId));
    }
}
