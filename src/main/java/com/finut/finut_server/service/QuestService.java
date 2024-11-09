package com.finut.finut_server.service;

import com.finut.finut_server.domain.quest.Quest;
import com.finut.finut_server.domain.quest.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestService {
    @Autowired
    private QuestRepository questRepository;

    public List<Quest> getAllQuests() {
        return questRepository.findAll();
    }
}
