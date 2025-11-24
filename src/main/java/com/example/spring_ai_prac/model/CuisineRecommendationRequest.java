package com.example.spring_ai_prac.model;

public record CuisineRecommendationRequest(

        String language,
        int amount,
        String country
) {
}
