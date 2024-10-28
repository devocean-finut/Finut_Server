package com.finut.finut_server.domain.difficulty;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "Difficulty")
public class Difficulty {

    @Id
    @Enumerated(EnumType.STRING)
    private DifficultyType difficulty;

    @Column
    private int diffQuizCnt;

}
