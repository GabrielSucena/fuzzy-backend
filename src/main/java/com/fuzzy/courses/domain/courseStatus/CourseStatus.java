package com.fuzzy.courses.domain.courseStatus;

import com.fuzzy.courses.domain.courseClassification.CourseClassification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    public enum Status {

        REALIZADO(1L, "Realizado"),
        A_REALIZAR(2L, "A Realizar");

        Status(long id, String status) {
            this.id = id;
            this.status = status;
        }

        private Long id;
        private String status;

        public CourseStatus get(){
            return new CourseStatus(id, status);
        }
    }

}
