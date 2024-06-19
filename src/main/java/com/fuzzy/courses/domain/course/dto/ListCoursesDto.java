package com.fuzzy.courses.domain.course.dto;

import com.fuzzy.courses.domain.course.Course;

import java.time.LocalDate;

public record ListCoursesDto(
        Long id,
        String title,
        String procedure,
        String instructorName,
        String workload,
        LocalDate date,
        String version
) {

    public ListCoursesDto (Course course) {
        this(course.getId(), course.getTitle(), course.getProcedure(), course.getInstructorName(), course.getWorkload(), course.getDate(), course.getVersion());
    }
}
