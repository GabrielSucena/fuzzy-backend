package com.fuzzy.courses.domain.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.modality.CourseModality;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterCourseDto(

        @NotBlank
        String instructorName,
        @NotBlank
        String version,
        @NotBlank
        String title,
        @NotBlank
        String workload,
        @NotBlank
        String procedure,
        @NotBlank
        String description,
        @Future
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate date,
        @NotNull
        CourseModality.Modalities courseModality
) {

    public Course toCourse() {
        return new Course(
                instructorName,
                version,
                title,
                workload,
                procedure,
                description,
                date,
                courseModality.get()
        );
    }

}
