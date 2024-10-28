package com.finut.finut_server.domain.difficulty;

import com.finut.finut_server.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyRepository extends JpaRepository<Difficulty, DifficultyType> {
}
