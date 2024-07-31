package com.fuzzy.courses.domain.collaborator.dto;

import java.time.LocalDate;

public interface ListCoursesCollaboratorDto {
    Long getId();
    String getClassification();
    String getStatus();
    String getCourse_title();
    String getCourse_version();
    LocalDate getStart_date();
    String getCourse_procedure();
}
