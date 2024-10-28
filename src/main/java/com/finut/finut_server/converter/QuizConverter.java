package com.finut.finut_server.converter;

import com.finut.finut_server.domain.quiz.Quiz;

public class QuizConverter {

    public static QuizResponseDTO.getQuizDto toGetQuizDto(Long userId, Quiz quiz){
        if(quiz == null) {
            return QuizResponseDTO.getQuizDto.builder()
                    .userId(userId)
                    .quiz(null)
                    .build();
        }
        return QuizResponseDTO.getQuizDto.builder()
                .userId(userId)
                .quiz(quiz)
                .build();
    }

    public static QuizResponseDTO.updateMoneyDto toUpdateMoneyDto(Long userId, int moneyAmount){
        return QuizResponseDTO.updateMoneyDto.builder()
                .userId(userId)
                .moneyAmount(moneyAmount)
                .build();
    }
}
