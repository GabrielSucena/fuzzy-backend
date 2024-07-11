package com.fuzzy.courses.domain.collaborator.dto;

import com.fuzzy.courses.domain.department.Department;
import com.fuzzy.courses.domain.position.Position;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCollaboratorDto(
        @NotBlank
        String fullName,
        @NotBlank
        @Email
        String email,
        @NotNull
        Position.Positions position,
        @NotNull
        Department.Departments department
) {
}
