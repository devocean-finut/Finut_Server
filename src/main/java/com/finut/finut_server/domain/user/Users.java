package com.finut.finut_server.domain.user;


import com.finut.finut_server.domain.BaseTimeEntity;
import com.finut.finut_server.domain.difficulty.Difficulty;
import com.finut.finut_server.domain.quizDone.QuizDone;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Getter
@Setter
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

    @Column(nullable = false)
    private String attend = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));



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
}
