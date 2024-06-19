package com.fuzzy.courses.domain.course.dto;

import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.course.Course;

import java.time.LocalDate;
import java.util.Set;

public record DetailCourseDto(
        Long id,
        String title,
        String procedure,
        String instructorName,
        String workload,
        LocalDate date,
        String version,
        Set<Collaborator> collaborators
) {

    public DetailCourseDto (Course course) {
        this(course.getId(), course.getTitle(), course.getProcedure(), course.getInstructorName(), course.getWorkload(), course.getDate(), course.getVersion(), course.getCollaborators());
    }

}
