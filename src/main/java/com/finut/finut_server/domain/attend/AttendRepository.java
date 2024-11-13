package com.finut.finut_server.domain.attend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendRepository extends JpaRepository<Attend, Attend.AttendId> {
    Optional<Attend> findByUserIdAndAttendDate(Long userId, String attendDate);

    @Query("SELECT attendDate FROM Attend WHERE user = :userId AND attendDate <= :today ORDER BY attendDate DESC LIMIT 5")
    List<String> findTop5ByUserIdAndDateBeforeOrderByDateDesc(Long userId, LocalDate today);
}

