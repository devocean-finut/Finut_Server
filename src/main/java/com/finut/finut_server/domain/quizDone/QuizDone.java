package com.finut.finut_server.domain.quizDone;

import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.user.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(QuizDoneId.class)
public class QuizDone {
    @Id
    private Long quizId;

    @Id
    private Long userId;

    private Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "quizId", insertable = false, updatable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private Users user;

}

