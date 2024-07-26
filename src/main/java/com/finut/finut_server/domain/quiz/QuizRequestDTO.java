package com.finut.finut_server.domain.quiz;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuizRequestDTO {
    @Getter
    public static class saveQuiz{
        @NotNull
        String content;
        @NotNull
        String answer;
        @NotNull
        String reason;
        @NotNull
        int correctMoney; // 맞춘 경우 얻는 돈
        @NotNull
        int wrongMoney; // 틀린 경우 얻는 돈
    }

}
