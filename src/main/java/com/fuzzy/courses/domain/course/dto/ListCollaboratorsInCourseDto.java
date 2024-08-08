package com.fuzzy.courses.domain.course.dto;

import java.time.LocalDate;

public interface ListCollaboratorsInCourseDto {
    Long getId();
    String getName();
    String getDepartment();
    String getPosition();
    String getClassification();
    String getStatus();
}
