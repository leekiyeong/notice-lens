package com.kiyoung.notice_lens.infra.storage;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileStorage {

    // 파일을 저장하고, 저장된 파일명을 반환함
    String store(MultipartFile file);

    // 저장된 파일명으로 실제 파일 경로를 찾음
    Path load(String storedFileName);
}
