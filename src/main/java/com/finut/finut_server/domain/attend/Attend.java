package com.finut.finut_server.domain.attend;

import com.finut.finut_server.domain.BaseTimeEntity;
import com.finut.finut_server.domain.purchases.Purchases;
import com.finut.finut_server.domain.user.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(Attend.AttendId.class)
public class Attend extends BaseTimeEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private Users user;

    @Id
    @Column(nullable = false)
    private String attendDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    public static class AttendId implements Serializable {
        private Long user;
        private String attendDate;
    }
}
