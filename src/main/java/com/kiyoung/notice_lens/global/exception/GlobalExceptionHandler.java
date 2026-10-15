package com.kiyoung.notice_lens.global.exception;

import com.kiyoung.notice_lens.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException e) {
        ErrorCode code = e.getErrorCode();
        log.warn("BusinessException errorCode={}, message={}", code.name(), code.getMessage());
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.error(code.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Void>> handleMaxUpload(MaxUploadSizeExceededException e) {
        ErrorCode code = ErrorCode.FILE_TOO_LARGE;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.error(code.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Unexpected error", e);
        ErrorCode code = ErrorCode.INTERNAL_ERROR;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.error(code.getMessage()));
    }
}
