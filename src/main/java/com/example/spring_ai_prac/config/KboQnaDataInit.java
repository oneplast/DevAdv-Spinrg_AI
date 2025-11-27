package com.example.spring_ai_prac.config;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ClassPathResource;

import com.example.spring_ai_prac.model.embed.KboQna;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Component
@RequiredArgsConstructor
public class KboQnaDataInit {

	private final VectorStore vectorStore;
	private final ObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		log.info("KBO 데이터 초기화 시작!");
		try {
			List<KboQna> qnaList = loadQnaFromJson();
			qnaList.forEach(this::upsert);
			log.info("KBO QNA 데이터 초기화 완료! 총 {}건", qnaList.size());
		} catch (Exception e) {
			log.error("KBO QNA 데이터 로딩 실패", e);
		}
	}

	public void upsert(KboQna k) {
		String content = """
			[질문]
			%s
			
			[답변]
			%s
			
			[카테고리] %s
			[키워드] %s
			""".formatted(
			k.question(),
			k.answer(),
			k.category(),
			String.join(", ", k.keywords()));

		Map<String, Object> meta = Map.of(
			"id", k.id(),
			"question", k.question(),
			"category", k.category(),
			"keyword", k.keywords()
		);

		Document doc = Document.builder()
			.id(k.id())
			.text(content)
			.metadata(meta)
			.build();

		vectorStore.add(List.of(doc));
	}

	private List<KboQna> loadQnaFromJson() throws Exception {

		ClassPathResource resource = new ClassPathResource("data/kbo-rule-qna.json");
		return objectMapper.readValue(
			resource.getInputStream(),
			new TypeReference<>() {
			}
		);
	}
}
