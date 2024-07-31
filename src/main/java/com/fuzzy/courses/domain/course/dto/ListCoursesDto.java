package com.fuzzy.courses.domain.course.dto;

import com.fuzzy.courses.domain.course.Course;

import java.time.LocalDate;

public record ListCoursesDto(
        Long id,
        String title,
        String procedure,
        String instructor,
        String workload,
        LocalDate startDate,
        LocalDate endDate,
        String version
) {

    public ListCoursesDto (Course course) {
        this(course.getId(), course.getTitle(), course.getProcedure(), course.getInstructor(), course.getWorkload(), course.getStartDate(), course.endDate() ,course.getVersion());
    }

}
