package com.fuzzy.courses.domain.course.dto;

import jakarta.validation.constraints.NotNull;

public record RemoveCollaboratorDto(
        @NotNull
        Long collaboratorId
) {
}
