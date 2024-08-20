package com.fuzzy.courses.domain.course.dto;

import com.fuzzy.courses.domain.collaborator.dto.DescribeCollaboratorDto;
import com.fuzzy.courses.domain.course.Course;

import java.time.LocalDate;
import java.util.List;

public record DetailCourseDto(
        Long id,
        String title,
        String description,
        String codification,
        String version,
        String instructor,
        LocalDate validityDate,
        String workload,
        LocalDate startDate,
        DescribeCourseDto describeCourse,
        List<ListCollaboratorsInCourseDto> collaborators

) {

    public DetailCourseDto (Course course, List<ListCollaboratorsInCourseDto> collaborators, DescribeCourseDto describeCourse) {
        this(course.getId(), course.getTitle(), course.getDescription() ,course.getCodification(), course.getVersion(), course.getInstructor(), course.validityDate() ,course.getWorkload(), course.getStartDate(), describeCourse,collaborators);
    }

}
