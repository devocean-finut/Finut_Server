package com.finut.finut_server.service;

import com.finut.finut_server.domain.questQuiz.QuestQuiz;
import com.finut.finut_server.domain.questQuiz.QuestQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestQuizService {

    @Autowired
    private QuestQuizRepository questQuizRepository;

    public List<QuestQuiz> getQuestQuizzes(Long questId) {
        return questQuizRepository.findByQuestId(questId);
    }
}
