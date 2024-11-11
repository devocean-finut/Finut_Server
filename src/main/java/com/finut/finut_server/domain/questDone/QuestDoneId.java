package com.finut.finut_server.domain.questDone;

import java.io.Serializable;

public class QuestDoneId implements Serializable {
    private Long questId;
    private Long userId;

    public QuestDoneId() {}

    public QuestDoneId(Long questId, Long userId) {
        this.questId = questId;
        this.userId = userId;
    }
}

