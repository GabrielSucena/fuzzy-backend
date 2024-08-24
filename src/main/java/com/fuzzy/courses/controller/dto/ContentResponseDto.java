package com.fuzzy.courses.controller.dto;

import lombok.Builder;

@Builder
public record ContentResponseDto(
        String content
) {
}
