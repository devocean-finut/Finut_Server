package com.finut.finut_server.converter;

import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.quiz.QuizRequestDTO;
import com.finut.finut_server.domain.quiz.QuizResponseDTO;

public class QuizConverter {
    public static QuizResponseDTO.saveQuizDto toSaveQuizDto(Quiz quiz){
        return QuizResponseDTO.saveQuizDto.builder()
                .quizId(quiz.getId())
                .quizContent(quiz.getContent())
                .quizAnswer(quiz.getAnswer())
                .quizReason(quiz.getReason())
                .build();
    }

    public static Quiz toQuiz(QuizRequestDTO.saveQuiz request){
        return Quiz.builder()
                .content(request.getContent())
                .answer(request.getAnswer())
                .reason(request.getReason())
                .correctMoney(request.getCorrectMoney())
                .wrongMoney(request.getWrongMoney())
                .build();
    }
}
