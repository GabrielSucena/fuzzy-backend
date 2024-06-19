package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.modality.CourseModality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseModalityRepository extends JpaRepository<CourseModality, Long> {
}
