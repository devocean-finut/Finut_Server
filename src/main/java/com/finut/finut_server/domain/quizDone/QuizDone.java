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

    @ManyToOne
    @MapsId("quizId")
    @JoinColumn(name = "quizId", insertable = false, updatable = false)
    private Quiz quiz;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private Users user;

    private Boolean isCorrect = false;

}

