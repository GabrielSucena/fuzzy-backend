package com.fuzzy.courses.domain.collaborator;

import com.fuzzy.courses.domain.department.Department;
import com.fuzzy.courses.domain.position.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Collaborator(String fullName, String collaboratorRecord, String email, Position position, Department department) {
        this.fullName = fullName;
        this.collaboratorRecord = collaboratorRecord;
        this.email = email;
        this.position = position;
        this.department = department;
    }
}
