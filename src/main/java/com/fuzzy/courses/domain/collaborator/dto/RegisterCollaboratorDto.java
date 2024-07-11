package com.fuzzy.courses.domain.collaborator.dto;


import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.department.Department;
import com.fuzzy.courses.domain.position.Position;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterCollaboratorDto(
        @NotBlank
        String fullName,
        @NotBlank
        @Pattern(regexp = "\\d{5}")
        String collaboratorRecord,
        @NotBlank
        @Email
        String email,
        @NotNull
        Position.Positions position,
        @NotNull
        Department.Departments department
) {

    public Collaborator toCollaborator() {
        return new Collaborator(
                fullName,
                collaboratorRecord,
                email,
                position.get(),
                department.get()
        );
    }
}
