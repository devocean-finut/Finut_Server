package com.finut.finut_server.domain.quiz;

import com.finut.finut_server.domain.difficulty.DifficultyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findByDifficulty(DifficultyType quizDiff);
    @Query(value = "(SELECT * FROM quiz WHERE difficulty = 'HI' ORDER BY RAND() LIMIT 3) " +
            "UNION ALL " +
            "(SELECT * FROM quiz WHERE difficulty = 'LO' ORDER BY RAND() LIMIT 3) " +
            "UNION ALL " +
            "(SELECT * FROM quiz WHERE difficulty = 'MI' ORDER BY RAND() LIMIT 3)",
            nativeQuery = true)
    List<Quiz> findQuizzesByDifficulty();
}
