package com.kiyoung.notice_lens.infra.llm;

public interface LlmClient {

    // 시스템 프롬프트와 사용자 지시를 받아 AI 답변을 돌려준다.
    String chat(String systemPrompt, String userMessage);

    // 사용한 모델 이름
    String modelName();
}
