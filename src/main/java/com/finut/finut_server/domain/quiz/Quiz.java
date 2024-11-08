package com.finut.finut_server.domain.quiz;


import com.finut.finut_server.domain.BaseTimeEntity;
import com.finut.finut_server.domain.difficulty.DifficultyType;
import com.finut.finut_server.domain.quizDone.QuizDone;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Quiz extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DifficultyType difficulty; // 난이도

    @Column(nullable = false)
    @Lob
    private String question; // 퀴즈 내용

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerType answer;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String description; // 정답 설명

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizDone> quizDoneList;

    public Quiz(Long quizId, DifficultyType difficultyType, String question, AnswerType answerType) {
        super();
    }
}
