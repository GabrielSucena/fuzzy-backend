package com.fuzzy.courses.domain.department;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

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

        public Department get(){
            return new Department(id, department);
        }
    }

}
