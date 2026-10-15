package com.kiyoung.notice_lens.AiController;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiTestController {

    private final ChatClient chatClient;

    public AiTestController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/ai/hello")
    public String hello(@RequestParam String q) {
        return chatClient.prompt().user(q).call().content();
    }
}
