package com.finut.finut_server.domain.quiz;


import com.finut.finut_server.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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
    private String question; // 퀴즈 내용

    @Lob
    @Column(nullable = false)
    private String option1;

    @Lob
    private String option2;

    @Lob
    private String option3;

    @Column(nullable = false)
    @Lob
    private String answer;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String description; // 정답 설명

    @Enumerated(EnumType.STRING)
    private QuizType quizType; // 퀴즈 타입: TF or MUL

    @Column(nullable = false)
    private int displayed; // 출제된지 여부 판단 (1: 출제된 적 있음, 0: 출제된 적 없음)

    @Column(nullable = false)
    private int correctMoney; // 맞춘 경우 얻는 돈

    @Column(nullable = false)
    private int wrongMoney; // 틀린 경우 얻는 돈
}
