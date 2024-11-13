package com.finut.finut_server.domain.quest;

import com.finut.finut_server.domain.level.LevelName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestDTO {
    private Long id;
    private LevelName nextLevel;

}
