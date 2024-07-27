package com.finut.finut_server.domain.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT e FROM Quiz e WHERE DATE(e.createdDate) = :date")
    Quiz findByDate(@Param("date") LocalDate date);
}
