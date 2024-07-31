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
        String name,

        @NotBlank
        @Pattern(regexp = "\\d{5}")
        String register,

        @NotBlank
        @Email
        String email,

        @NotNull
        Long positionId,

        @NotNull
        Long departmentId
) {

        public Collaborator toCollaborator(Position position, Department department) {
                return new Collaborator(
                        name,
                        register,
                        email,
                        position,
                        department
                );
        }

}
