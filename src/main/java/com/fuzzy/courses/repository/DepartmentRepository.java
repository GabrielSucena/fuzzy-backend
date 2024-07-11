package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
