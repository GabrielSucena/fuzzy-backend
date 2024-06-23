package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.courseClassification.CourseClassification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseClassificationRepository extends JpaRepository<CourseClassification, Long> {
}
