package com.kiyoung.notice_lens.infra.document;

import com.kiyoung.notice_lens.global.exception.BusinessException;
import com.kiyoung.notice_lens.global.exception.ErrorCode;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
public class PdfBoxDocumentParser implements DocumentParser{

    // PDFBOX로 PDF에서 TEXT 추출
    @Override
    public String extractText(Path path){
        try (PDDocument document = Loader.loadPDF(path.toFile())) {
            PDFTextStripper stripper = new PDFTextStripper();
            // 화면에 보이는 위치 순서대로 글자를 정렬
            stripper.setSortByPosition(true);
            return stripper.getText(document);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.TEXT_EXTRACTION_FAILED);
        }
    }
}
