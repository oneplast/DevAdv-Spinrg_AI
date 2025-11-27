package com.example.spring_ai_prac.controller;

import com.example.spring_ai_prac.model.CuisineRecommendationRequest;
import com.example.spring_ai_prac.model.CuisineRecommendationResponse;
import com.example.spring_ai_prac.model.FitnessProgramConsultantRequest;
import com.example.spring_ai_prac.services.AiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/prompt/v1")
    public String showPromptV1Page() {

        return "prompt/fitness";
    }

    @PostMapping("/prompt/v1")
    public String generateCustomizedFitnessProgram(
            FitnessProgramConsultantRequest request,
            Model model
    ) {

        String response = aiService.generateCustomizedFitnessProgram(request);
        model.addAttribute("response", response);

        return "prompt/fitness";
    }

    @GetMapping("/prompt/v2")
    public String showPromptV2Page() {

        return "prompt/cuisine";
    }

    @PostMapping("/prompt/v2")
    public String generatePromptAnswer(
            CuisineRecommendationRequest request,
            Model model
    ) {

        CuisineRecommendationResponse response = aiService.generateCuisineList(request);
        model.addAttribute("response", response);

        return "prompt/cuisine";
    }

    @GetMapping("/embedding/v1")
    public String showEmbeddingV1Page() {

        return "embedding/p1";
    }

    @PostMapping("/embedding/v1")
    public String embeddingData(
            @RequestParam("q") String q,
            Model model
    ) {

        float[] embed = aiService.embed(q);
        model.addAttribute("result", embed);

        return "embedding/p1";
    }

    @GetMapping("/embedding/v2")
    public String showEmbeddingV2Page() {

        return "embedding/p2";
    }

    @PostMapping("/embedding/v2")
    public String similaritySearch(
            @RequestParam("d1") String d1,
            @RequestParam("d2") String d2,
            Model model
    ) {

        double similarity = aiService.getSimilarity(d1, d2);
        model.addAttribute("similarity", similarity);

        return "embedding/p2";
    }

    @GetMapping("/embedding/v3")
    public String showEmbeddingV3Page() {

        return "embedding/p3";
    }

    @PostMapping("/embedding/v3")
    public String findDocsFromVectorStore(
            @RequestParam("q") String q,
            Model model
    ) {

        List<Document> documents = aiService.searchFruits(q);
        model.addAttribute("results", documents);

        return "embedding/p3";
    }

    @GetMapping("/rag/v1")
    public String showRagV1Page() {

        return "rag/p1";
    }

    @PostMapping("/rag/v1")
    public String generateKboAnswer(
            @RequestParam("q") String q,
            Model model
    ) {

        String response = aiService.generateRagAnswer(q);
        model.addAttribute("response", response);

        return "rag/p1";
    }
}
