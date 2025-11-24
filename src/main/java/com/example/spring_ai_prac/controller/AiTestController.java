package com.example.spring_ai_prac.controller;

import com.example.spring_ai_prac.services.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AiTestController {

    private final AiService aiService;

    @GetMapping("/text/chat")
    public String showTextChatPage() {

        return "text/chat";
    }

    @PostMapping("/text/chat")
    @ResponseBody
    public String generateAnswer(
            @RequestParam("q") String q
    ) {

        log.info("q = {}", q);

//        ChatResponse chatResponse = aiService.generateAnswer(q);
        ChatResponse chatResponse = aiService.generateAnswerWithRoles(q);
        String result = chatResponse.getResult().getOutput().getText();

        log.info("result = {}", result);

        return result;
    }

    @GetMapping("/stream/chat")
    public String showStreamChatPage() {

        return "text/stream";
    }

    @GetMapping("/stream/chat/answer")
    @ResponseBody
    public Flux<String> generateStreamAnswer(
            @RequestParam("q") String q
    ) {

        return aiService.generateStreamAnswer(q);
    }
}
