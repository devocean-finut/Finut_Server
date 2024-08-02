package com.finut.finut_server.domain.user;


import com.finut.finut_server.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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
    private String accessToken;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ColumnDefault("0")
    private Long money;

    @Builder
    public Users(String name, String email, String picture, String accessToken, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.accessToken = accessToken;
        this.role = role;
    }

    public Users update(String name, String picture, String accessToken) {
        this.name = name;
        this.picture = picture;
        this.accessToken = accessToken;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
