package com.example.spring_ai_prac.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class AiService {

    private final ChatClient client;

    public AiService(ChatClient.Builder chatClientBuilder) {
        this.client = chatClientBuilder.build();
    }

    public ChatResponse generateAnswer(String question) {

        OpenAiChatOptions options = new OpenAiChatOptions();
        options.setModel("llama-3.1-8b-instant");

        return client.prompt(new Prompt(question, options)).call().chatResponse();

    }
}
