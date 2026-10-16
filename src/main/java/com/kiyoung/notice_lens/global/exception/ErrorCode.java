package com.kiyoung.notice_lens.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    // 파일
    EMPTY_FILE(HttpStatus.BAD_REQUEST, "업로드 할 파일이 없습니다."),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, "PDF 파일만 업로드 할 수 있습니다."),
    FILE_TOO_LARGE(HttpStatus.BAD_REQUEST, "파일 크기가 너무 큽니다."),
    FILE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장에 실패했습니다."),

    // 공고문
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "공고문을 찾을 수 없습니다."),
    TEXT_EXTRACTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "PDF 텍스트 추출에 실패했습니다."),
    TEXT_NOT_FOUND(HttpStatus.UNPROCESSABLE_CONTENT, "PDF에서 텍스트를 찾을 수 없습니다."),

    // AI 관련
    NOTICE_NOT_EXTRACT(HttpStatus.CONFLICT, "텍스트 추출이 완료되지 않은 공고문입니다."),
    LLM_CALL_FAILED(HttpStatus.BAD_GATEWAY, "AI 호출에 실패했습니다.");

    private final HttpStatus status;
    private final String message;
}
