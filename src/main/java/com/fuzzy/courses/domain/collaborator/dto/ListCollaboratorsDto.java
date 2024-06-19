package com.fuzzy.courses.domain.collaborator.dto;

import com.fuzzy.courses.domain.collaborator.Collaborator;

public record ListCollaboratorsDto(
        Long id,
        String collaboratorRecord,
        String fullName,
        String collaboratorDepartment,
        String collaboratorPosition
) {

    public ListCollaboratorsDto(Collaborator collaborator){
        this(collaborator.getId(), collaborator.getCollaboratorRecord(), collaborator.getFullName(), collaborator.getCollaboratorDepartment().getDepartment(), collaborator.getCollaboratorPosition().getPosition());
    }

}
