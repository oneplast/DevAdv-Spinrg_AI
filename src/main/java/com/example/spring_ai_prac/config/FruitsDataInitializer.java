package com.example.spring_ai_prac.config;

import com.example.spring_ai_prac.model.embed.FruitDescription;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FruitsDataInitializer {

    private final VectorStore vectorStore;

    @PostConstruct
    public void init() {
        log.info("과일 데이터 초기화 시작!");
        generateFruitList().forEach(this::upsert);
        log.info("과일 데이터 초기화 끝!");
    }

    public void upsert(FruitDescription f) {

        String content = """
                과일명: %s
                설명: %s
                당도: %d
                부드러움: %d
                계절: %s
                """.formatted(
                f.name(),
                f.description(),
                f.sweetness(),
                f.softness(),
                f.season());

        Map<String, Object> meta = Map.of(
                "name", f.name(),
                "sweetness", f.sweetness(),
                "softness", f.softness(),
                "season", f.season()
        );

        Document doc = Document.builder()
                .id("fruit_%s".formatted(f.name()))
                .text(content)
                .metadata(meta)
                .build();

        vectorStore.add(List.of(doc));
    }

    private List<FruitDescription> generateFruitList() {
        return List.of(
                new FruitDescription("사과", "상큼한 산미와 적당한 단단함을 가진 과일이며 저장성이 좋습니다.", 6, 3, "가을"),
                new FruitDescription("배", "과즙이 풍부하고 시원한 단맛이 강한 한국 전통 품종이 많은 과일입니다.", 7, 4, "가을"),
                new FruitDescription("바나나", "달콤하고 부드러운 식감을 가지며 에너지 공급원으로 자주 섭취됩니다.", 8, 8, "연중"),
                new FruitDescription("딸기", "향이 진하고 산미와 단맛의 균형이 좋아 생과로 많이 소비됩니다.", 6, 5, "겨울~봄"),
                new FruitDescription("블루베리", "작은 크기지만 향이 진하고 산미가 높으며 항산화 성분이 풍부합니다.", 5, 4, "여름"),
                new FruitDescription("포도", "품종에 따라 달콤함과 향이 크게 달라지며 생과 또는 주스로 널리 이용됩니다.", 7, 5, "여름~가을"),
                new FruitDescription("오렌지", "상큼한 산미와 적당한 단맛이 어우러져 주스용으로 자주 선택됩니다.", 5, 3, "겨울"),
                new FruitDescription("복숭아", "향이 강하고 부드러운 과육을 가지며 과즙이 풍부합니다.", 7, 8, "여름"),
                new FruitDescription("자두", "강한 산미와 상쾌한 풍미가 특징이며 과육이 단단한 편입니다.", 4, 3, "여름"),
                new FruitDescription("망고", "매우 달고 진한 향을 지닌 열대과일이며 과육이 부드럽고 부드러운 섬유질이 있습니다.", 9, 7, "여름")
        );
    }
}
