package com.fuzzy.courses.domain.course.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateStatusDto(
        @NotNull
        Long collaboratorId
) {
}
