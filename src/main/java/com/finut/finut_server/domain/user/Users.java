package com.finut.finut_server.domain.user;


import com.finut.finut_server.domain.BaseTimeEntity;
import com.finut.finut_server.domain.difficulty.Difficulty;
import com.finut.finut_server.domain.quizDone.QuizDone;
import com.finut.finut_server.domain.level.Level;
import com.finut.finut_server.domain.level.LevelName;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String refreshToken;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Long money = 100000L;

    @OneToOne
    @JoinColumn(name = "levelId", referencedColumnName = "id")
    private Level level;

    @Column(nullable = false)
    private int attendCount = 0;

    @Column(nullable = false)
    private int XP = 0;

    @Column(nullable = false)
    private boolean todaySalary = false;

    @Column(nullable = false)
    private int diffQuizCount = 0;

    @Column(nullable = false)
    private int levelQuizCount = 0;



    @OneToOne
    @JoinColumn(name = "difficulty")
    private Difficulty difficulty;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<QuizDone> quizDoneList;


    @Builder
    public Users(String name, String email, String picture, String refreshToken, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.refreshToken = refreshToken;
        this.role = role;
    }

    public Users update(String name, String picture, String refreshToken) {
        this.name = name;
        this.picture = picture;
        this.refreshToken = refreshToken;
        return this;
    }

    public Users setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    @PrePersist
    public void prePersist() {
        if (this.level == null) {
            this.level = new Level(); // 또는 LevelRepository를 사용해 ID가 1인 Level을 설정
            this.level.setId(1L); // 기본값으로 ID가 1인 Level 설정
        }
    }
}
