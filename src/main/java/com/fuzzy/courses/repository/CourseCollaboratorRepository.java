package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.collaborator.dto.ListCoursesCollaboratorDto;
import com.fuzzy.courses.domain.courseCollaborator.CourseCollaborator;
import com.fuzzy.courses.domain.pk.CourseCollaboratorPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseCollaboratorRepository extends JpaRepository<CourseCollaborator, CourseCollaboratorPK> {

    @Query(
            value = """
                    SELECT B.classification as classification, C.status as status, D.course_title as course_title, D.course_version as course_version, D.date as date, D.course_procedure as course_procedure
                    FROM courses_collaborators A
                    JOIN course_classification B
                    ON A.course_classification_id = B.id
                    JOIN course_status C
                    ON A.course_status_id = C.id
                    JOIN courses D
                    ON A.course_id = D.id
                    WHERE A.collaborator_id = :id
                    """,
            nativeQuery=true)
    List<ListCoursesCollaboratorDto> listCourses(Long id);

}
