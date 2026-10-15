package com.kiyoung.notice_lens.infra.storage;

import com.kiyoung.notice_lens.global.exception.BusinessException;
import com.kiyoung.notice_lens.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class LocalFileStorage implements FileStorage {

    // 파일을 저장 할 주소 객체 생성
    private final Path root;

    // 반환타입이 없고 클래스 이름과 같으면 "생성자"
    // @Value는 application.yml에 적어둔 값을 매개변수 storagePath에 담음
    public LocalFileStorage(@Value("${app.storage.path}") String storagePath) {
        // this는 지금 만들어진 객체 자신 => private final Path root를 나타냄
        this.root = Paths.get(storagePath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new IllegalStateException("저장 폴더를 만들 수 없습니다: " + root, e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String storedFileName = UUID.randomUUID() + ".pdf";
        try(InputStream in = file.getInputStream()) {
            Files.copy(in, root.resolve(storedFileName));
            return storedFileName;
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_SAVE_FAILED);
        }
    }

    @Override
    public Path load(String storedFileName) {
        return root.resolve(storedFileName).normalize();
    }
}
