package com.fuzzy.courses.controller.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ContentResponseDto(
        List<String> records
) {
}
