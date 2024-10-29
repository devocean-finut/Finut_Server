package com.finut.finut_server.domain.level;

import com.finut.finut_server.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Level extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LevelName level; // 직급

    @Column(nullable = false)
    private int salary; // 월급 액수

    @Column(nullable = false)
    private int levelQuizCnt = 5; // 승진을 위한 퀴즈 수
}
