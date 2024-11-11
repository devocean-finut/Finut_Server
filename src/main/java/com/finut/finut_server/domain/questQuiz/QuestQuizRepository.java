package com.finut.finut_server.domain.questQuiz;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestQuizRepository extends JpaRepository<QuestQuiz, Long> {
    List<QuestQuiz> findByQuestId(Long questId);
}
