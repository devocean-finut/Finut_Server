package com.finut.finut_server.domain.level;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Integer> {

    Level findById(Long id);
}
