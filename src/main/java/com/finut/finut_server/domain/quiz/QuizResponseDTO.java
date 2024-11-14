package com.finut.finut_server.domain.quiz;

import com.finut.finut_server.domain.difficulty.DifficultyType;
import com.finut.finut_server.domain.level.LevelName;
import lombok.*;
import org.apache.commons.lang3.builder.Diff;

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

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class quizResultResponseDTO {
        String difficulty;
    }
}
