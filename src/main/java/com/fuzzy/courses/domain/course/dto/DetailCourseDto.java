package com.fuzzy.courses.domain.course.dto;

import com.fuzzy.courses.domain.collaborator.dto.DescribeCollaboratorDto;
import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.courseCollaborator.CourseCollaborator;

import java.time.LocalDate;
import java.util.Set;

public record DetailCourseDto(
        Long id,
        String title,
        String procedure,
        String instructor,
        String workload,
        LocalDate startDate,
        String version,
        String modality,
        Integer validityYears,
        String codification,
        Set<CourseCollaborator> listCollaborators

) {

    public DetailCourseDto (Course course) {
        this(course.getId(), course.getTitle(), course.getProcedure(), course.getInstructor(), course.getWorkload(), course.getStartDate(), course.getVersion(), course.getModality().getModality(), course.getValidityYears(), course.getCodification().getCodification(), course.getCourseCollaborators());
    }

}
