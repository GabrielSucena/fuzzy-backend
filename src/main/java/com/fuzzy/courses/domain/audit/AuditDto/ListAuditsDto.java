package com.fuzzy.courses.domain.audit.AuditDto;

import com.fuzzy.courses.domain.audit.Audit;
import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

public record ListAuditsDto(
        Long id,
        String user,
        Instant createdOn,
        Long course,
        Long collaborator,
        String changedField,
        String oldValues,
        Boolean removed,
        String courseVersion,
        String reason

) {

    public ListAuditsDto(Audit audit) {
        this(audit.getId(), audit.getUser(), audit.getCreatedOn(), audit.getCourse(), audit.getCollaborator(), audit.getChangedField(), audit.getOldValues(), audit.getRemoved(), audit.getCourseVersion(), audit.getReason());
    }
}
