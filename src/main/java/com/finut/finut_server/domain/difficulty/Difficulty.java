package com.finut.finut_server.domain.difficulty;

import com.finut.finut_server.domain.user.Users;
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

    @Column(nullable = false)
    private int diffQuizCnt;

    @OneToOne(mappedBy = "difficulty")
    private Users user;
}
