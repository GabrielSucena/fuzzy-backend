package com.fuzzy.courses.domain.department;

import com.fuzzy.courses.domain.position.CollaboratorPosition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "collaborator_departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollaboratorDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String department;

    public enum Departments {

        PeD(1L, "PeD");

        Departments(long id, String department) {
            this.id = id;
            this.department = department;
        }

        private Long id;
        private String department;

        public CollaboratorDepartment get(){
            return new CollaboratorDepartment(id, department);
        }
    }

}
