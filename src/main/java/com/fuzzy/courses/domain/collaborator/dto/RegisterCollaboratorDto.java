package com.fuzzy.courses.domain.collaborator.dto;


import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.department.CollaboratorDepartment;
import com.fuzzy.courses.domain.position.CollaboratorPosition;
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
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String phone,
        @NotNull
        CollaboratorPosition.Positions collaboratorPosition,
        @NotNull
        CollaboratorDepartment.Departments collaboratorDepartment
) {

    public Collaborator toCollaborator() {
        return new Collaborator(
                fullName,
                collaboratorRecord,
                email,
                phone,
                collaboratorPosition.get(),
                collaboratorDepartment.get()
        );
    }
}
