package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.collaborator.dto.DescribeCollaboratorDto;
import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.course.dto.DescribeCourseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(
            value = """
                    SELECT
                        c.id AS course_id,
                        (c.start_date + INTERVAL '30' DAY) AS end_date,
                        CASE
                        WHEN :today < c.start_date THEN 'Agendado'
                        WHEN :today > (c.start_date + INTERVAL '30' DAY) THEN 'Encerrado'
                        WHEN :today BETWEEN c.start_date AND (c.start_date + INTERVAL '30' DAY) THEN 'Em andamento'
                        END AS status,
                    
                        -- Contagem de cursos completados
                        (SELECT COUNT(*)
                        FROM collaborators e
                        JOIN courses_collaborators ce ON e.id = ce.collaborator_id
                        WHERE ce.course_id = c.id
                        AND ce.completed_date IS NOT NULL
                        ) AS green,
                    
                        -- Contagem para a primeira quinzena
                        (SELECT COUNT(*)
                        FROM collaborators e
                        JOIN courses_collaborators ce ON e.id = ce.collaborator_id
                        WHERE ce.course_id = c.id
                        AND ce.completed_date IS NULL
                        AND :today BETWEEN (c.start_date + INTERVAL '0' DAY) AND (c.start_date + INTERVAL '15' DAY)
                        ) AS yellow,
                    
                        -- Contagem para a segunda quinzena
                        (SELECT COUNT(*)
                        FROM collaborators e
                        JOIN courses_collaborators ce ON e.id = ce.collaborator_id
                        WHERE ce.course_id = c.id
                        AND ce.completed_date IS NULL
                        AND :today BETWEEN (c.start_date + INTERVAL '15' DAY) AND (c.start_date + INTERVAL '30' DAY)
                        ) AS orange,
                    
                        -- Contagem para cursos expirados
                        (SELECT COUNT(*)
                        FROM collaborators e
                        JOIN courses_collaborators ce ON e.id = ce.collaborator_id
                        WHERE ce.course_id = c.id
                        AND ce.completed_date IS NULL
                        AND :today > (c.start_date + INTERVAL '30' DAY)
                        ) AS red,
                    
                        -- Contagem de status
                        (SELECT COUNT(CASE WHEN ce.classification_id = 1 THEN 1 END)
                        FROM courses_collaborators ce
                        WHERE ce.course_id = c.id
                        ) AS na,
                        (SELECT COUNT(CASE WHEN ce.classification_id = 2 THEN 1 END)
                        FROM courses_collaborators ce
                        WHERE ce.course_id = c.id
                        ) AS menor,
                        (SELECT COUNT(CASE WHEN ce.classification_id = 3 THEN 1 END)
                        FROM courses_collaborators ce
                        WHERE ce.course_id = c.id
                        ) AS maior,
                        (SELECT COUNT(CASE WHEN ce.classification_id = 4 THEN 1 END)
                        FROM courses_collaborators ce
                        WHERE ce.course_id = c.id
                        ) AS critico,
                        -- Contagem de quantidade
                        (SELECT COUNT(*)
                        FROM courses_collaborators ce
                        WHERE ce.course_id = c.id
                        ) AS quantidade
                    
                    FROM
                       courses c
                    WHERE
                       c.id = :id
                    GROUP BY
                       c.id, c.start_date
                    """,
            nativeQuery=true)
    DescribeCourseDto describeCourse(Long id, LocalDate today);

}
