package com.finut.finut_server.service;

import com.finut.finut_server.domain.quest.Quest;
import com.finut.finut_server.domain.quest.QuestDTO;
import com.finut.finut_server.domain.quest.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestService {
    @Autowired
    private QuestRepository questRepository;

    public List<QuestDTO> getAllQuests() {
        List<Quest> quests= questRepository.findAll();
        return quests.stream()
                .map(quest -> new QuestDTO(quest.getId(), quest.getNextLevel()))
                .collect(Collectors.toList());
    }
}
