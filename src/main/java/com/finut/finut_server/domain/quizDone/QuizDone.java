package com.finut.finut_server.domain.quizDone;

import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class QuizDone {

    @EmbeddedId
    private QuizDoneId id;

    @Column(nullable = false)
    private Boolean isCorrect = false;

}
