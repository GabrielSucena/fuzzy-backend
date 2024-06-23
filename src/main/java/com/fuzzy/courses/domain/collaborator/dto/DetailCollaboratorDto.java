package com.fuzzy.courses.domain.collaborator.dto;

import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.courseCollaborator.CourseCollaborator;

import java.util.Set;

public record DetailCollaboratorDto(
        Long id,
        String fullName,
        String collaboratorRecord,
        String email,
        String phone,
        String collaboratorDepartment,
        String collaboratorPosition
) {


    public DetailCollaboratorDto(Collaborator collaborator) {
        this(collaborator.getId(), collaborator.getFullName(), collaborator.getCollaboratorRecord(), collaborator.getEmail(), collaborator.getPhone(), collaborator.getCollaboratorDepartment().getDepartment(), collaborator.getCollaboratorPosition().getPosition());
    }
}
