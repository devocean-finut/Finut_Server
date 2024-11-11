package com.finut.finut_server.domain.questDone;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(QuestDoneId.class)
public class QuestDone {
    @Id
    private Long questId;

    @Id
    private Long userId;
}
