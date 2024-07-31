package com.fuzzy.courses.domain.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateCourseDto(
        @NotBlank
        String instructor,

        @NotBlank
        String title,

        @NotBlank
        String workload,

        String description,

        @JsonFormat(pattern = "dd/MM/yyyy")
        @NotNull
        LocalDate startDate,

        @NotNull
        Integer validityYears,

        @NotNull
        Long codingsId
) {
}
