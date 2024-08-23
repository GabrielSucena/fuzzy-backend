package com.fuzzy.courses.controller.dto;

import com.fuzzy.courses.domain.role.Role;

import java.util.Set;

public record LoginResponse(
        String accessToken,
        Long expiresIn,
        String role
) {
}
