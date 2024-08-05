package com.finut.finut_server.domain.quiz;


import com.finut.finut_server.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Quiz extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content; // 퀴즈 내용

    @Column(nullable = false)
    private String answer; // 퀴즈 정답

    @Column(nullable = false)
    private String reason; // 정답 이유

    @Column(nullable = false)
    private int correctMoney; // 맞춘 경우 얻는 돈

    @Column(nullable = false)
    private int wrongMoney; // 틀린 경우 얻는 돈
}
