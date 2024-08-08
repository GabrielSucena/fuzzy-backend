package com.fuzzy.courses.domain.course;
import com.fuzzy.courses.domain.courseCollaborator.CourseCollaborator;
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

    @Column(name = "codification")
    private String codification;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "validity_years")
    private Integer validityYears;

    @Column(name = "created_on")
    @CreationTimestamp
    private Instant createdOn;

    @OneToMany(mappedBy = "id.course")
    private Set<CourseCollaborator> courseCollaborators = new HashSet<>();

    public Course(String instructor, String version, String title, String workload, String codification, String description, LocalDate startDate, Integer validityYears) {
        this.instructor = instructor;
        this.version = version;
        this.title = title;
        this.workload = workload;
        this.codification = codification;
        this.description = description;
        this.startDate = startDate;
        this.validityYears = validityYears;
    }

    public LocalDate validityDate() {
        return this.startDate.plusYears(this.validityYears);
    }
}
