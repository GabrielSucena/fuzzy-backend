package com.fuzzy.courses.domain.course.dto.courseCollaborator;

import java.util.Set;

public record AddCollaboratorDto(
        Set<Long> collaboratorsId,
        Set<Long> departmentsId
) {
}
