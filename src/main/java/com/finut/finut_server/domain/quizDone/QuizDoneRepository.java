package com.finut.finut_server.domain.quizDone;

import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizDoneRepository extends JpaRepository<QuizDone, QuizDoneId> {
    boolean existsByIdQuizIdAndIdUserId(Long quizId, Long userId);

    Optional<QuizDone> findByIdUserId(Long userId);
}
