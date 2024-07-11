package com.fuzzy.courses.domain.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fuzzy.courses.domain.codification.Codification;
import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.modality.Modality;
import com.fuzzy.courses.domain.validity.Validity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateCourseDto(

        @NotBlank
        String instructorName,
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
        Modality.Modalities modality,
        @NotNull
        Validity.Validities validities,
        @NotNull
        Codification.Codings codings
) {

    public Course toCourse() {
        return new Course(
                instructorName,
                title,
                workload,
                procedure,
                description,
                date,
                modality.get(),
                validities.get(),
                codings.get()
        );
    }

}
