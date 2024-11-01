package com.finut.finut_server.service;

import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.quizDone.QuizDone;
import com.finut.finut_server.domain.quizDone.QuizDoneRepository;
import com.finut.finut_server.domain.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizDoneService {

    @Autowired
    QuizDoneRepository quizDoneRepository;

    QuizDone quizDone;

    public void saveQuizDone(Users user, Optional<Quiz> quiz, boolean isCorrect) {
        quizDone.setQuiz(quiz);
        quizDone.setUser(user);
        quizDone.setIsCorrect(isCorrect);
        quizDoneRepository.save(quizDone);
    }

}
