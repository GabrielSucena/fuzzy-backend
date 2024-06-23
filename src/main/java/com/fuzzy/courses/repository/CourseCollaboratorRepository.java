package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.courseCollaborator.CourseCollaborator;
import com.fuzzy.courses.domain.pk.CourseCollaboratorPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseCollaboratorRepository extends JpaRepository<CourseCollaborator, CourseCollaboratorPK> {
}
