package com.finut.finut_server.service;

import com.finut.finut_server.apiPayload.code.status.ErrorStatus;
import com.finut.finut_server.apiPayload.exception.GeneralException;
import com.finut.finut_server.controller.QuizController;
import com.finut.finut_server.converter.QuizConverter;
import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.quiz.QuizRepository;
import com.finut.finut_server.domain.quiz.QuizRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Transactional
    public Quiz saveQuiz(QuizRequestDTO.saveQuiz request){
        if(request.getCorrectMoney() < 0 || request.getWrongMoney() < 0){
            throw new GeneralException(ErrorStatus.INVALID_NUMBER);
        }

        Quiz quiz = QuizConverter.toQuiz(request);
        quiz = quizRepository.save(quiz);

        return quiz;
    }

    @Transactional
    public Quiz getQuiz(){
        LocalDate today = LocalDate.now();
        return quizRepository.findByDate(today);
    }

    @Transactional
    public int updateMoney(QuizRequestDTO.updateMoney request){
        int moneyAmount = 0;
        if(request.isCorrect()) moneyAmount = request.getCorrectMoney();
        else moneyAmount = request.getWrongMoney();

        quizRepository.updateMoney(request.getUserId(), moneyAmount);
        return moneyAmount;
    }
}
