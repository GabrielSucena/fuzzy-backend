package com.fuzzy.courses.domain.collaborator;

import com.fuzzy.courses.domain.department.Department;
import com.fuzzy.courses.domain.position.Position;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "name")
    private String name;

    @Column(name = "register", unique = true)
    private String register;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Collaborator(String name, String register, String email, Position position, Department department) {
        this.name = name;
        this.register = register;
        this.email = email;
        this.position = position;
        this.department = department;
    }
}
