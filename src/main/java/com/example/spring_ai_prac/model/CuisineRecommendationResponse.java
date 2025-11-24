package com.example.spring_ai_prac.model;

import java.util.List;

public record CuisineRecommendationResponse(

        String country,
        List<String> cuisines
) {
}
