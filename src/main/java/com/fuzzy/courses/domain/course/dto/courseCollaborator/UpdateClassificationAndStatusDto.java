package com.fuzzy.courses.domain.course.dto.courseCollaborator;

import jakarta.validation.constraints.NotNull;

public record UpdateClassificationAndStatusDto(
        @NotNull
        Long collaboratorId,
        Long classificationId,
        Long statusId
) {
}
