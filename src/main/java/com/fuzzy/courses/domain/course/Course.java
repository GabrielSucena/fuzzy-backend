package com.fuzzy.courses.domain.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.modality.CourseModality;
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

    @Column(name = "course_workload")
    private String workload;

    @Column(name = "course_procedure")
    private String procedure;

    @Column(name = "course_description")
    private String description;

    @Column(name = "course_date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "course_modality_id")
    private CourseModality courseModality;

    @ManyToMany
    @JoinTable(
            name = "courses_collaborators",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "collaborator_id")
    )
    @JsonIgnore
    private Set<Collaborator> collaborators = new HashSet<>();

    public Course(String instructorName, String version, String title, String workload, String procedure, String description, LocalDate date, CourseModality courseModality) {
        this.instructorName = instructorName;
        this. version = version;
        this.title = title;
        this.workload = workload;
        this.procedure = procedure;
        this.description = description;
        this.date = date;
        this.courseModality = courseModality;
    }
}
