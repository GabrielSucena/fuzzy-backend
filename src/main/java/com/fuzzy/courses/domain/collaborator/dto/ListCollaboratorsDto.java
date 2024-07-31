package com.fuzzy.courses.domain.collaborator.dto;

import com.fuzzy.courses.domain.collaborator.Collaborator;

public record ListCollaboratorsDto(
        Long id,
        String register,
        String name,
        String department,
        String position
) {

    public ListCollaboratorsDto(Collaborator collaborator){
        this(collaborator.getId(), collaborator.getRegister(), collaborator.getName(), collaborator.getDepartment().getDepartment(), collaborator.getPosition().getPosition());
    }

}
