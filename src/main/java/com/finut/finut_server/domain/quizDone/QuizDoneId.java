package com.finut.finut_server.domain.quizDone;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class QuizDoneId implements Serializable {
    private Long quizId;
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
