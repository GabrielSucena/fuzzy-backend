package com.fuzzy.courses.domain.course.dto.courseCollaborator;

import jakarta.validation.constraints.NotNull;

public record RemoveCollaboratorDto(
        @NotNull
        Long collaboratorId
) {
}
