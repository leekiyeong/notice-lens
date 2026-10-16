package com.kiyoung.notice_lens.infra.llm;

import com.kiyoung.notice_lens.global.exception.BusinessException;
import com.kiyoung.notice_lens.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// Spring.AI 가 이미 모델 교체를 지원하는데 왜 인터페이스를 사용하는가?
// 분석 Service가 Spring AI라는 프레임워크에 묶이지 않으며, 모델별 후처리를 한곳에 모을 수 있음.
@Slf4j
@Component
public class SpringAiLlmClient implements LlmClient {

    private final ChatClient chatClient;
    private final String modelName;

    public SpringAiLlmClient(ChatClient.Builder builder, @Value("${spring.ai.ollama.chat.options.model}") String modelName) {
        this.chatClient = builder.build();
        this.modelName = modelName;
    }

    @Override
    public String chat(String systemPrompt, String userMessage) {
        try {
            String content = chatClient.prompt()
                    .system(systemPrompt)
                    .user(userMessage)
                    .call()
                    .content();
            return removeThinking(content);
        } catch (Exception e){
            log.error("LLM CALL FAILED", e);
            throw new BusinessException(ErrorCode.LLM_CALL_FAILED);
        }
    }

    @Override
    public String modelName() {
        return modelName;
    }

    private String removeThinking(String content) {
        if (content == null) {
            return null;
        }
        return content.replace("(?s)<think>.*?</think>", "").strip();
    }
}
