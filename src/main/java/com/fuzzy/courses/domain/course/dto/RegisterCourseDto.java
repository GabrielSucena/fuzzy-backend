package com.fuzzy.courses.domain.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fuzzy.courses.domain.codification.Codification;
import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.modality.Modality;
import jakarta.validation.constraints.Future;
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
        String procedure,

        String description,

        @JsonFormat(pattern = "dd/MM/yyyy")
                @NotNull
        LocalDate startDate,

        @NotNull
        Integer validityYears,

        @NotNull
        Long modalityId,

        @NotNull
        Long codingsId
) {

    public Course toCourse(Modality modality, Codification codification) {
        return new Course(
                instructor,
                version,
                title,
                workload,
                procedure,
                description,
                startDate,
                validityYears,
                modality,
                codification
        );
    }

}
