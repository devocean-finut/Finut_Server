package com.finut.finut_server.domain.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class QuizResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class saveQuizDto{
        Long quizId;
        String quizContent; // 퀴즈 내용
        String quizAnswer; // 퀴즈 답
        String quizReason; // 답 풀이
        int correctMoney; // 맞춘 경우 얻는 돈
        int wrongMoney; // 틀린 경우 얻는 돈
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getQuizDto{
        Long userId; // 접속한 유저의 아이디
        String quizContent; // 퀴즈 내용
        String quizAnswer; // 퀴즈 답
        String quizReason; // 답 풀이
        int correctMoney; // 맞춘 경우 얻는 돈
        int wrongMoney; // 틀린 경우 얻는 돈
    }
}
