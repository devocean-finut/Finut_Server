package com.finut.finut_server.domain.quiz;

import com.finut.finut_server.domain.difficulty.DifficultyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class QuizResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class randomQuizResponseDTO {
        Long id;
        DifficultyType difficulty;
        String question;
        AnswerType answer;
        String description;
    }
}
