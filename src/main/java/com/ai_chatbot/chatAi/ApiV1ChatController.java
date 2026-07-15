package com.ai_chatbot.chatAi;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/v1/chat")
@RestController
@RequiredArgsConstructor
public class ApiV1ChatController {
    private final ChatClient chatClient;

    @GetMapping("/ai")
    public Map<String, String> chat(@RequestParam String message,
                                    @RequestParam(defaultValue = "default") String conversationId // 추가 - conversationId 기본 값 default 설정
    ) {
        String response = chatClient.prompt()
                .user(message)
                // 추가 - 대화방 ID, 이 값으로 이전 대화를 꺼내오고 응답도 저장함
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();

        return Map.of("응답 : ", response);
    }
}
