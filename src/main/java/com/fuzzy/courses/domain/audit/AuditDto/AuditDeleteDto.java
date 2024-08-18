package com.fuzzy.courses.domain.audit.AuditDto;

import jakarta.validation.constraints.NotBlank;

public record AuditDeleteDto(
        @NotBlank
        String reason
) {
}
