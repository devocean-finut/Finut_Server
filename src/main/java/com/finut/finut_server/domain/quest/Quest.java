package com.finut.finut_server.domain.quest;

import com.finut.finut_server.domain.BaseTimeEntity;
import com.finut.finut_server.domain.difficulty.DifficultyType;
import com.finut.finut_server.domain.questQuiz.QuestQuiz;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestQuiz> quizzes;
}
