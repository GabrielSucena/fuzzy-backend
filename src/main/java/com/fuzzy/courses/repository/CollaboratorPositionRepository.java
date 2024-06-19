package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.position.CollaboratorPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaboratorPositionRepository extends JpaRepository<CollaboratorPosition, Long> {
}
