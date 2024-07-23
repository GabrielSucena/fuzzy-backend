package com.fuzzy.courses.domain.collaborator.dto;

import java.time.LocalDate;

public interface ListCoursesCollaboratorDto {
    String getClassification();
    String getStatus();
    String getCourse_title();
    String getCourse_version();
    LocalDate getDate();
    String getCourse_procedure();
}
