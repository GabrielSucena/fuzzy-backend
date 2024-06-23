package com.fuzzy.courses.domain.course.dto;

import com.fuzzy.courses.domain.courseClassification.CourseClassification;
import com.fuzzy.courses.domain.courseStatus.CourseStatus;
import jakarta.validation.constraints.NotNull;

public record AddCollaboratorDto(
        @NotNull
        Long collaboratorId) {
}
