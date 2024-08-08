package com.fuzzy.courses.domain.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fuzzy.courses.domain.course.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterCourseDto(
        @NotBlank
        String instructor,

        @NotBlank
        String version,

        @NotBlank
        String title,

        @NotBlank
        String workload,

        @NotBlank
        String codification,

        String description,

        @JsonFormat(pattern = "dd/MM/yyyy")
                @NotNull
        LocalDate startDate,

        @NotNull
        Integer validityYears
) {

    public Course toCourse() {
        return new Course(
                instructor,
                version,
                title,
                workload,
                codification,
                description,
                startDate,
                validityYears
        );
    }

}
