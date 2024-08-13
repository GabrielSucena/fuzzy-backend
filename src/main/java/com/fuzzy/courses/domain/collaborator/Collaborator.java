package com.fuzzy.courses.domain.collaborator;

import com.fuzzy.courses.controller.dto.LoginRequest;
import com.fuzzy.courses.domain.department.Department;
import com.fuzzy.courses.domain.position.Position;
import com.fuzzy.courses.domain.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "collaborator_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public Collaborator(String name, String register, String email, Position position, Department department, Role role, String password) {
        this.name = name;
        this.register = register;
        this.email = email;
        this.position = position;
        this.department = department;
        this.setRoles(Set.of(role));
        this.password = password;
    }

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {

        return passwordEncoder.matches(loginRequest.password(), this.password);

    }
}
