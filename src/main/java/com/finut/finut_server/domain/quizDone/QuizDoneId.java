package com.finut.finut_server.domain.quizDone;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class QuizDoneId  implements Serializable {
    private Long quizId;
    private Long userId;

    // Default constructor, hashCode, equals
    @Override
    public int hashCode(){
        return Objects.hash(quizId, userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizDoneId that = (QuizDoneId) o;
        return Objects.equals(quizId, that.quizId) && Objects.equals(userId, that.userId);
    }
}
