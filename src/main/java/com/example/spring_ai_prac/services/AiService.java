package com.example.spring_ai_prac.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class AiService {

    private final ChatClient client;

    public AiService(ChatClient.Builder chatClientBuilder) {
        this.client = chatClientBuilder.build();
    }

    public ChatResponse generateAnswer(String question) {

        return client.prompt(question).call().chatResponse();
    }
}
