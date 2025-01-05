package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.audit.Audit;
import com.fuzzy.courses.domain.audit.AuditDto.ListAuditsDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditRepository extends JpaRepository<Audit, Long> {


    List<ListAuditsDto> findByCollaborator(Long id);

    List<ListAuditsDto> findByCourse(Long id);
}
