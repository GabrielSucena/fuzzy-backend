package com.fuzzy.courses.domain.collaborator.dto;

import jakarta.validation.constraints.NotBlank;

public record PatchCollaboratorDto(
        @NotBlank
        String name,
        @NotBlank
        String reason
) {
}
