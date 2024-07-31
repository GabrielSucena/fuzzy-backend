package com.fuzzy.courses.domain.position.dto;

import com.fuzzy.courses.domain.position.Position;

public record ListPositionsDto(
        Long id,
        String position
) {

    public ListPositionsDto(Position position) {
        this(position.getId(), position.getPosition());
    }

}
