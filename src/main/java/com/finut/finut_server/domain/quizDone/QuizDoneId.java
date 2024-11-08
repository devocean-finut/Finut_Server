package com.finut.finut_server.domain.quizDone;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Embeddable
public class QuizDoneId implements Serializable {

    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "user_id")
    private Long userId;

    // equals 메서드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizDoneId that = (QuizDoneId) o;
        return Objects.equals(quizId, that.quizId) &&
                Objects.equals(userId, that.userId);
    }

    // hashCode 메서드
    @Override
    public int hashCode() {
        return Objects.hash(quizId, userId);
    }
}
