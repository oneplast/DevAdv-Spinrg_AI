package com.example.spring_ai_prac.services;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient client;

    public ChatResponse generateAnswer(String question) {

        return client.prompt(question).call().chatResponse();
    }

    public Flux<String> generateStreamAnswer(String question) {

        return client.prompt(question).stream().content();
    }
}
