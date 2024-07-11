package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.position.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
