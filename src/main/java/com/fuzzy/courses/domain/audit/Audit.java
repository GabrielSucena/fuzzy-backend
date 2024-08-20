package com.fuzzy.courses.domain.audit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "audits")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_modify")
    private String user;

    @Column(name = "created_on")
    @CreationTimestamp
    private Instant createdOn;

    @Column(name = "course_modify")
    private Long course;

    @Column(name = "collaborator_modify")
    private Long collaborator;

    @Column(name = "changed_field")
    private String changedField;

    @Column(name = "old_values")
    private String oldValues;

    @Column(name = "removed")
    private Boolean removed;

    @Column(name = "course_version")
    private String courseVersion;

    @Column(name = "reason_removed")
    private String reason;

    public Audit(String user, Long course, Long collaborator, String changedField, String oldValues, Boolean removed, String courseVersion, String reason) {
        this.user = user;
        this.course = course;
        this.collaborator = collaborator;
        this.changedField = changedField;
        this.oldValues = oldValues;
        this.removed = removed;
        this.courseVersion = courseVersion;
        this.reason = reason;
    }
}
