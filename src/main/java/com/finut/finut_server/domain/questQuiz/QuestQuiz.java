package com.finut.finut_server.domain.questQuiz;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.finut.finut_server.domain.quest.Quest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class QuestQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    @Column(nullable = false)
//    private Long quizId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id", insertable = false, updatable = false)
    @JsonBackReference
    private Quest quest;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String option1;

    @Column(nullable = false)
    private String option2;

    @Column(nullable = false)
    private String option3;

    @Column(nullable = false)
    private Integer correctOption;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Lob
    private String description;


}
