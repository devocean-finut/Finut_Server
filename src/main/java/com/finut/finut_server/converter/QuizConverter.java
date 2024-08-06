package com.finut.finut_server.converter;

import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.quiz.QuizRequestDTO;
import com.finut.finut_server.domain.quiz.QuizResponseDTO;
import org.springframework.security.core.parameters.P;

public class QuizConverter {
    public static QuizResponseDTO.saveQuizDto toSaveQuizDto(Quiz quiz){
        return QuizResponseDTO.saveQuizDto.builder()
                .quizId(quiz.getId())
                .quizContent(quiz.getContent())
                .quizAnswer(quiz.getAnswer())
                .quizReason(quiz.getReason())
                .correctMoney(quiz.getCorrectMoney())
                .wrongMoney(quiz.getWrongMoney())
                .build();
    }

    public static Quiz toQuiz(QuizRequestDTO.saveQuiz request){
        return com.finut.finut_server.domain.quiz.Quiz.builder()
                .content(request.getContent())
                .answer(request.getAnswer())
                .reason(request.getReason())
                .correctMoney(request.getCorrectMoney())
                .wrongMoney(request.getWrongMoney())
                .build();
    }

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
