package com.example.spring_ai_prac;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInit implements ApplicationRunner {

    private final VectorStore vectorStore;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try {
            Document document = new Document("Init Data", Map.of("type", "init"));

            vectorStore.add(List.of(document));

            log.info("크로마 디비 초기화 성공!");
        } catch (Exception e) {
            log.error("크로마 디비 초기화 중 오류 발생!", e);
        }
    }
}
