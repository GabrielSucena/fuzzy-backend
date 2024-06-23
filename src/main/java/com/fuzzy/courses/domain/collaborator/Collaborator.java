package com.fuzzy.courses.domain.collaborator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.courseCollaborator.CourseCollaborator;
import com.fuzzy.courses.domain.department.CollaboratorDepartment;
import com.fuzzy.courses.domain.position.CollaboratorPosition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "collaborators")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Collaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "collaborator_record", unique = true)
    private String collaboratorRecord;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "collaborator_position_id")
    private CollaboratorPosition collaboratorPosition;

    @ManyToOne
    @JoinColumn(name = "collaborator_department_id")
    private CollaboratorDepartment collaboratorDepartment;

    public Collaborator(String fullName, String collaboratorRecord, String email, String phone, CollaboratorPosition collaboratorPosition, CollaboratorDepartment collaboratorDepartment) {
        this.fullName = fullName;
        this.collaboratorRecord = collaboratorRecord;
        this.email = email;
        this.phone = phone;
        this.collaboratorPosition = collaboratorPosition;
        this.collaboratorDepartment = collaboratorDepartment;
    }
}
