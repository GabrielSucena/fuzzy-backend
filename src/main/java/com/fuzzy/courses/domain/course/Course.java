package com.fuzzy.courses.domain.course;

import com.fuzzy.courses.domain.codification.Codification;
import com.fuzzy.courses.domain.courseCollaborator.CourseCollaborator;
import com.fuzzy.courses.domain.modality.Modality;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "instructor")
    private String instructor;

    @Column(name = "course_version")
    private String version;

    @Column(name = "course_title")
    private String title;

    @Column(name = "workload")
    private String workload;

    @Column(name = "course_procedure")
    private String procedure;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "validity_years")
    private Integer validityYears;

    @Column(name = "created_on")
    @CreationTimestamp
    private Instant createdOn;

    @ManyToOne
    @JoinColumn(name = "modality_id")
    private Modality modality;

    @ManyToOne
    @JoinColumn(name = "codification_id")
    private Codification codification;

    @OneToMany(mappedBy = "id.course")
    private Set<CourseCollaborator> courseCollaborators = new HashSet<>();

    public Course(String instructor, String version, String title, String workload, String procedure, String description, LocalDate startDate, Integer validityYears, Modality modality, Codification codification) {
        this.instructor = instructor;
        this.version = version;
        this.title = title;
        this.workload = workload;
        this.procedure = procedure;
        this.description = description;
        this.startDate = startDate;
        this.validityYears = validityYears;
        this.modality = modality;
        this.codification = codification;
    }

    public LocalDate endDate() {
        return this.startDate.plusYears(this.validityYears);
    }
}
