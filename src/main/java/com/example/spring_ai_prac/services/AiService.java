package com.example.spring_ai_prac.services;

import com.example.spring_ai_prac.model.CuisineRecommendationRequest;
import com.example.spring_ai_prac.model.CuisineRecommendationResponse;
import com.example.spring_ai_prac.model.FitnessProgramConsultantRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
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

    public ChatResponse generateAnswerWithRoles(String question) {

        return client.prompt(question)
                .system("You are a helpful assistant that can answer any question. You must answer in Korean")
                .user(question)
                .call()
                .chatResponse();
    }

    public String generateCustomizedFitnessProgram(FitnessProgramConsultantRequest request) {

        PromptTemplate promptTemplate = new PromptTemplate("""
                너는 맞춤 피트니스 운동을 설계해주는 전문가야.
                
                고객의 신체정보를 받아서, 원하는 부위의 운동을 추천해줄거야.
                
                다음과 같은 형식으로 대답해.
                
                - 대답 형식
                {targetPart} 운동을 고민하시는군요!
                
                {workoutExpMonth}개월 정도의 운동 경력을 갖고 계시고,
                
                신장 {height}cm, 체중 {weight}kg 정도라면 다음의 운동을 추천합니다!
                
                """
        );

        Map<String, Object> params = Map.of(
                "targetPart", request.targetPart(),
                "height", request.height(),
                "weight", request.weight(),
                "workoutExpMonth", request.workoutExpMonth()
        );

        Prompt prompt = promptTemplate.create(params);

        return client.prompt(prompt).call().chatResponse().getResult().getOutput().getText();
    }

    public CuisineRecommendationResponse generateCuisineList(CuisineRecommendationRequest request) {

        PromptTemplate promptTemplate = new PromptTemplate("""
                You are an expert in traditional cuisines.
                Answer the question: What is the traditional cuisine of {country}?
                Return a list of {amount} in {language} language.
                
                You provide information about a specific dish from a specific country.
                """.trim());

        Prompt prompt = promptTemplate.create(
                Map.of(
                        "country", request.country(),
                        "amount", request.amount(),
                        "language", request.language()
                )
        );

        return client.prompt(prompt).call().entity(CuisineRecommendationResponse.class);
    }
}
