package com.kiyoung.notice_lens.domain.notice.service;

import com.kiyoung.notice_lens.domain.notice.dto.NoticeExtractResponse;
import com.kiyoung.notice_lens.domain.notice.dto.NoticeResponse;
import com.kiyoung.notice_lens.domain.notice.dto.NoticeUploadResponse;
import com.kiyoung.notice_lens.domain.notice.entity.Notice;
import com.kiyoung.notice_lens.domain.notice.repository.NoticeRepository;
import com.kiyoung.notice_lens.global.exception.BusinessException;
import com.kiyoung.notice_lens.global.exception.ErrorCode;
import com.kiyoung.notice_lens.infra.document.DocumentParser;
import com.kiyoung.notice_lens.infra.storage.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final FileStorage fileStorage;
    private final DocumentParser documentParser;

    // 업로드 매서드
    @Transactional
    public NoticeUploadResponse upload(String title, MultipartFile file) {
        validatePdf(file);

        String storedFileName = fileStorage.store(file);

        Notice notice = Notice.create(
                title,
                file.getOriginalFilename(),
                storedFileName,
                file.getSize()
        );
        noticeRepository.save(notice);

        return NoticeUploadResponse.from(notice);
    }

    // 삭제 매서드
    @Transactional
    public void delete(Long id) {
        Notice notice = getActiveNotice(id);
        notice.delete();
    }

    // 조회 매서드 id
    public NoticeResponse findById(Long id) {
        return NoticeResponse.from(getActiveNotice(id));
    }

    // 조회 매서드 all
    public List<NoticeResponse> findAll() {
        return noticeRepository.findAllByDeletedFalseOrderByCreatedAtDesc().stream()
                .map(NoticeResponse::from)
                .toList();
    }


    // 이 클래스 안에서만 쓰기 때문에 private 처리
    private void validatePdf(MultipartFile file) {
        if(file == null || file.getSize() <= 0 || file.isEmpty()) {
            throw new BusinessException(ErrorCode.EMPTY_FILE);
        }
        String name = file.getOriginalFilename();
        if(name == null || !name.endsWith(".pdf")) {
            throw new BusinessException(ErrorCode.INVALID_FILE_TYPE);
        }
    }

    // 삭제되지 않은것만 조회
    private Notice getActiveNotice(Long id) {
        return noticeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTICE_NOT_FOUND));
    }

    // PDFBOX로 TEXT 추출
    @Transactional
    public NoticeExtractResponse extractText(Long id) {
        // 삭제 안된 공고문 찾기
        Notice notice = getActiveNotice(id);
        // 저장 경로 가져오기
        Path path = fileStorage.load(notice.getStoredFileName());
        String text = documentParser.extractText(path);
        if(text == null ||  text.isEmpty()) {
            throw new BusinessException(ErrorCode.TEXT_NOT_FOUND);
        }

        // 추출 시 상태를 EXTRACTED로 변경
        notice.completeExtraction(text.strip());
        return NoticeExtractResponse.from(notice);
    }
}
