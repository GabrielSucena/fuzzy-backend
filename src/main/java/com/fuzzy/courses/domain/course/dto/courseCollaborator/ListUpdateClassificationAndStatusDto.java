package com.fuzzy.courses.domain.course.dto.courseCollaborator;

import java.util.List;

public record ListUpdateClassificationAndStatusDto(
        List<UpdateClassificationAndStatusDto> updateClassificationAndStatusDtoList
) {
}
