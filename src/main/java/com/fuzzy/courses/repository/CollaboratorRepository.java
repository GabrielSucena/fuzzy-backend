package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.collaborator.dto.DescribeCollaboratorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {

    Optional<Collaborator> findByRegisterOrEmail(String register, String email);

    @Query(
            value = """
                    SELECT
                                                                                 
                                                           -- Contagem de cursos completados
                                                           (SELECT COUNT(*)
                                                           FROM courses c
                                                           LEFT JOIN courses_collaborators ce ON c.id = ce.course_id
                                                           WHERE ce.collaborator_id = :id
                                                           AND ce.completed_date IS NOT NULL
                                                           ) AS green,
                                                                                  
                                                           -- Contagem para a primeira quinzena
                                                           (SELECT COUNT(*)
                                                           FROM courses c
                                                           LEFT JOIN courses_collaborators ce ON c.id = ce.course_id
                                                           WHERE ce.collaborator_id = :id
                                                           AND ce.completed_date IS NULL
                                                           AND :today BETWEEN (c.start_date + INTERVAL '0' DAY) AND (c.start_date + INTERVAL '15' DAY)
                                                           ) AS yellow,
                                                                                   
                                                           -- Contagem para a segunda quinzena
                                                           (SELECT COUNT(*)
                                                           FROM courses c
                                                           LEFT JOIN courses_collaborators ce ON c.id = ce.course_id
                                                           WHERE ce.collaborator_id = :id
                                                           AND ce.completed_date IS NULL
                                                           AND :today BETWEEN (c.start_date + INTERVAL '15' DAY) AND (c.start_date + INTERVAL '30' DAY)
                                                           ) AS orange,
                                                                                  
                                                           -- Contagem para cursos expirados
                                                           (SELECT COUNT(*)
                                                           FROM courses c
                                                           LEFT JOIN courses_collaborators ce ON c.id = ce.course_id
                                                           WHERE ce.collaborator_id = :id
                                                           AND ce.completed_date IS NULL
                                                           AND :today > (c.start_date + INTERVAL '30' DAY)
                                                           ) AS red,
                                                           
                                                           -- Contagem de cursos por tipo (X) para o empregado
                                                           (SELECT COUNT(*)
                                                           FROM courses c
                                                           LEFT JOIN courses_collaborators ce ON c.id = ce.course_id
                                                           WHERE ce.collaborator_id = :id
                                                           AND c.codification LIKE '%SOP%'
                                                           ) AS sop,
                                                                                   
                                                           -- Contagem de cursos por tipo (Y) para o empregado
                                                           (SELECT COUNT(*)
                                                           FROM courses c
                                                           LEFT JOIN courses_collaborators ce ON c.id = ce.course_id
                                                           WHERE ce.collaborator_id = :id
                                                           AND c.codification LIKE '%GOP%'
                                                           ) AS gop                     
                                                           FROM
                                                           collaborators e
                                                           LEFT JOIN
                                                           positions j ON e.position_id = j.id
                                                           LEFT JOIN
                                                           departments d ON e.department_id = d.id
                                                           WHERE
                                                           e.id = :id
                    """,
            nativeQuery=true)
    DescribeCollaboratorDto describeCollaborator(Long id, LocalDate today);

    List<Collaborator> findByDepartment_id(Long departmentId);

    Optional<Collaborator> findByRegister(String register);

    Optional<Collaborator> findByName(String name);
}
