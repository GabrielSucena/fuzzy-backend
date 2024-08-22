package com.fuzzy.courses.domain.course.dto.courseCollaborator;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AlterStatusByRegisterDto(
        @NotNull
        List<String> registers
) {
}
