package com.kiyoung.notice_lens.domain.notice.controller;

import com.kiyoung.notice_lens.domain.notice.dto.NoticeResponse;
import com.kiyoung.notice_lens.domain.notice.dto.NoticeUploadResponse;
import com.kiyoung.notice_lens.domain.notice.entity.Notice;
import com.kiyoung.notice_lens.domain.notice.service.NoticeService;
import com.kiyoung.notice_lens.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 업로드
    // ResponseEntity<ApiResponse<NoticeUploadResponse>> 반환타입 ->
    // NoticeUploadResponse: 실제 데이터
    // ApiResponse: 데이터를 success, data, message로 감싼 공통 응답
    // ResponseEntity: HTTP 상태 코드 추가
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<NoticeUploadResponse>> upload(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file) {

        // 받은값을 Service에 전달하고, 결과 DTO를 받음
        NoticeUploadResponse response = noticeService.upload(title, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(response));
    }

    // 단건 id로 조회
    @GetMapping("/{id}")
    public ApiResponse<NoticeResponse> findById(@PathVariable Long id) {
        return ApiResponse.ok(noticeService.findById(id));
    }

    // 목록 조회
    @GetMapping
    public ApiResponse<List<NoticeResponse>> findAll() {
        return ApiResponse.ok(noticeService.findAll());
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
