package com.fuzzy.courses.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDto(
        @NotBlank
        String password
) {
}
