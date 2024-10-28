package com.finut.finut_server.domain.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

//    @Query("SELECT e FROM Quiz e WHERE DATE(e.createdDate) = :date")
//    Quiz findByDate(@Param("date") LocalDate date);

//    @Modifying
//    @Transactional
//    @Query("UPDATE Users u SET u.money = u.money + :moneyAmount WHERE u.id = :userId")
//    void updateMoney(Long userId, int moneyAmount);
}
