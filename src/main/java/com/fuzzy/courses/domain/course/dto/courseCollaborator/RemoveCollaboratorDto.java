package com.fuzzy.courses.domain.course.dto.courseCollaborator;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record RemoveCollaboratorDto(
        Set<Long> collaboratorsId,
        Set<Long> departmentsId,
        @NotBlank
        String reason
) {
}
