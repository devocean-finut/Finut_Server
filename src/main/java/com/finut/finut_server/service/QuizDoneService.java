package com.finut.finut_server.service;

import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.quizDone.QuizDone;
import com.finut.finut_server.domain.quizDone.QuizDoneId;
import com.finut.finut_server.domain.quizDone.QuizDoneRepository;
import com.finut.finut_server.domain.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizDoneService {

    @Autowired
    QuizDoneRepository quizDoneRepository;



    public void saveQuizDone(Users user, Quiz quiz, boolean isCorrect) {
        QuizDone quizDone = new QuizDone();
        QuizDoneId quizDoneId = new QuizDoneId();

        quizDoneId.setQuizId(quiz.getId());
        quizDoneId.setUserId(user.getId());

        quizDone.setQuizId(quiz.getId());
        quizDone.setUserId(user.getId());

        quizDone.setQuiz(quiz);
        quizDone.setUser(user);
        quizDone.setIsCorrect(isCorrect);
        quizDoneRepository.save(quizDone);
    }

}
