package com.fuzzy.courses.controller.dto;

public record LoginResponse(
        String accessToken,
        Long expiresIn
) {
}
