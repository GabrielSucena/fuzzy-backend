package com.fuzzy.courses.domain.course.dto;

import com.fuzzy.courses.domain.courseClassification.CourseClassification;
import jakarta.validation.constraints.NotNull;

public record UpdateClassificationDto(
        @NotNull
        Long collaboratorId,
        @NotNull
        CourseClassification.Classifications classifications
) {
}
