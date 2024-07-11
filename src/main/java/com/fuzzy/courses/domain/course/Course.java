package com.fuzzy.courses.domain.course;

import com.fuzzy.courses.domain.codification.Codification;
import com.fuzzy.courses.domain.courseCollaborator.CourseCollaborator;
import com.fuzzy.courses.domain.modality.Modality;
import com.fuzzy.courses.domain.validity.Validity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "instructor_name")
    private String instructorName;

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

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "modality_id")
    private Modality modality;

    @ManyToOne
    @JoinColumn(name = "validity_id")
    private Validity validity;

    @ManyToOne
    @JoinColumn(name = "codification_id")
    private Codification codification;

    @OneToMany(mappedBy = "id.course")
    private Set<CourseCollaborator> courseCollaborators = new HashSet<>();

    public Course(String instructorName, String version, String title, String workload, String procedure, String description, LocalDate date, Modality modality, Validity validity, Codification codification) {
        this.instructorName = instructorName;
        this. version = version;
        this.title = title;
        this.workload = workload;
        this.procedure = procedure;
        this.description = description;
        this.date = date;
        this.modality = modality;
        this.validity = validity;
        this.codification = codification;
    }

    public Course(String instructorName, String title, String workload, String procedure, String description, LocalDate date, Modality modality, Validity validity, Codification codification) {
        this.instructorName = instructorName;
        this.title = title;
        this.workload = workload;
        this.procedure = procedure;
        this.description = description;
        this.date = date;
        this.modality = modality;
        this.validity = validity;
        this.codification = codification;
    }
}
