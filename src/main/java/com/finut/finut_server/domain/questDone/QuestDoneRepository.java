package com.finut.finut_server.domain.questDone;

import com.finut.finut_server.domain.quest.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestDoneRepository extends JpaRepository<QuestDone, Long> {
    List<QuestDone> findAllByUserId(Long userid);
}
