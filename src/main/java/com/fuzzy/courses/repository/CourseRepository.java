package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
