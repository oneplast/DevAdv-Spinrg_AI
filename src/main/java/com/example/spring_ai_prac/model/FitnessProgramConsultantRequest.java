package com.example.spring_ai_prac.model;

public record FitnessProgramConsultantRequest(

        String targetPart,
        String height,
        String weight,
        String workoutExpMonth
) {
}
