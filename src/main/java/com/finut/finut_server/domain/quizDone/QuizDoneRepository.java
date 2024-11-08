package com.finut.finut_server.domain.quizDone;

import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface QuizDoneRepository extends JpaRepository<QuizDone, QuizDoneId> {
    Optional<QuizDone> findByQuizIdAndUserId(Long quizId, Long userId);

    Optional<QuizDone> findByUserId(Long userId);
}
