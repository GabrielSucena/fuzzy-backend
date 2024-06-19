package com.fuzzy.courses.domain.collaborator.dto;

import jakarta.validation.constraints.NotNull;

public record RemoveCourseDto(
        @NotNull
        Long courseId
) {
}
