package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.department.CollaboratorDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaboratorDepartmentRepository extends JpaRepository<CollaboratorDepartment, Long> {
}
