package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.audit.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {
}
