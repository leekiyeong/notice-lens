package com.kiyoung.notice_lens.infra.document;
import java.nio.file.Path;

public interface DocumentParser {

    // 파일 경로를 받아서 텍스트를 추출한다.
    String extractText(Path path);
}
