package com.finut.finut_server.service;

import com.finut.finut_server.apiPayload.code.status.ErrorStatus;
import com.finut.finut_server.apiPayload.exception.GeneralException;
import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.quiz.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

//    @Transactional
//    public Quiz getQuiz(){
//        // displayed == 0 이면 조회 가능
//        Quiz quiz;
//        while(true){
//            Random random = new Random();
//            Long randomNumber = random.nextLong(260) + 1; // 1부터 260까지의 숫자를 랜덤하게 뽑음
//            quiz = quizRepository.findById(randomNumber)
//                    .orElseThrow(() -> new GeneralException(ErrorStatus.INVALID_NUMBER));
//            if(quiz.getDisplayed() == 0) {
//                quiz.setDisplayed(1);
//                quiz = quizRepository.save(quiz);
//                break;
//            }
//        }
//        return quiz;
//    }

}
