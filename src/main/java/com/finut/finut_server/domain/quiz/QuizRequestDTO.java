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
    public static class updateMoney{
        @NotNull
        Long userId; // 접속한 유저의 아이디
        @NotNull
        boolean isCorrect; // 정답 여부 - 정답이면 true, 오답이면 false
        @NotNull
        int correctMoney; // 맞춘 경우 받는 돈
        @NotNull
        int wrongMoney; // 틀린 경우 받는 돈
    }

}
