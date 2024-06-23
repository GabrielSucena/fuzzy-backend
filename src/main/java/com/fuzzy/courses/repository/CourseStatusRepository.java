package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.courseStatus.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseStatusRepository extends JpaRepository<CourseStatus, Long> {
}
