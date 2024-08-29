package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.collaborator.dto.ListCoursesCollaboratorDto;
import com.fuzzy.courses.domain.course.dto.ListCollaboratorsInCourseDto;
import com.fuzzy.courses.domain.courseCollaborator.CourseCollaborator;
import com.fuzzy.courses.domain.courseCollaborator.pk.CourseCollaboratorPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CourseCollaboratorRepository extends JpaRepository<CourseCollaborator, CourseCollaboratorPK> {

    @Query(
            value = """
                    SELECT B.classification as classification, C.status as status, D.id as id ,D.course_title as course_title, D.course_version as course_version, D.start_date as start_date, D.codification as codification
                    FROM courses_collaborators A
                    JOIN classifications B
                    ON A.classification_id = B.id
                    JOIN status C
                    ON A.status_id = C.id
                    JOIN courses D
                    ON A.course_id = D.id
                    WHERE A.collaborator_id = :id
                    """,
            nativeQuery=true)
    List<ListCoursesCollaboratorDto> listCourses(Long id);

    @Query(
            value = """
                    SELECT C.id as id, C.name as name, D.department as department, P.position as position, CL.classification as classification, S.status as status
                    FROM courses_collaborators CC
                    JOIN collaborators C
                    ON CC.collaborator_id = C.id
                    JOIN departments D
                    ON C.department_id = D.id
                    JOIN positions P
                    ON C.position_id = P.id
                    JOIN classifications CL
                    ON CC.classification_id = CL.id
                    JOIN status S
                    ON CC.status_id = S.id
                    WHERE CC.course_id = :id
                    """,
            nativeQuery=true)
    List<ListCollaboratorsInCourseDto> listCollaborators(Long id);

    @Query(
            value = """
                    SELECT C.course_title
                    FROM courses_collaborators CC
                    JOIN courses C
                    ON CC.course_id = C.id
                    WHERE CC.collaborator_id = :id
                    AND cc.status_id = 2
                    """,
            nativeQuery=true
    )
    List<String> findUserCourses(Long id);

    @Query(
            value = """
                    SELECT c.id
                    FROM courses_collaborators CC
                    JOIN collaborators C
                    ON cc.collaborator_id = C.id
                    WHERE cc.course_id = :id
                    AND cc.status_id = 2
                    """,
            nativeQuery=true
    )
    List<Long> findUsersWithPending(Long id);
}
