package com.fuzzy.courses.domain.collaborator.dto;

import com.fuzzy.courses.domain.collaborator.Collaborator;

public record DetailCollaboratorDto(
        Long id,
        String fullName,
        String collaboratorRecord,
        String email,
        String department,
        String position
) {


    public DetailCollaboratorDto(Collaborator collaborator) {
        this(collaborator.getId(), collaborator.getFullName(), collaborator.getCollaboratorRecord(), collaborator.getEmail(), collaborator.getDepartment().getDepartment(), collaborator.getPosition().getPosition());
    }
}
