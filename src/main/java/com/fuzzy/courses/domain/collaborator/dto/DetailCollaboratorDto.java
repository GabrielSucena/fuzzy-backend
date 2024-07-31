package com.fuzzy.courses.domain.collaborator.dto;

import com.fuzzy.courses.domain.collaborator.Collaborator;

import java.util.List;

public record DetailCollaboratorDto(
        Long id,
        String name,
        String register,
        String email,
        String department,
        String position,
        DescribeCollaboratorDto describeCollaborator,
        List<ListCoursesCollaboratorDto> courses
) {

    public DetailCollaboratorDto(Collaborator collaborator, List<ListCoursesCollaboratorDto> courses, DescribeCollaboratorDto describeCollaborator) {
        this(collaborator.getId(), collaborator.getName(), collaborator.getRegister(), collaborator.getEmail(), collaborator.getDepartment().getDepartment(), collaborator.getPosition().getPosition(), describeCollaborator, courses);
    }

}
