package com.fuzzy.courses.domain.audit.AuditDto;

import com.fuzzy.courses.domain.audit.Audit;
import java.util.List;

public record AuditDto(
        String user,
        Long courseId,
        Long collaboratorId,
        String changedField,
        String oldValues,
        Boolean removed,
        String courseVersion,
        String reason
) {

    public Audit toAudit(AuditDto audit){
        return new Audit(
                user,
                courseId,
                collaboratorId,
                changedField,
                oldValues,
                removed,
                courseVersion,
                reason
        );
    }

}
