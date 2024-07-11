package com.fuzzy.courses.domain.courseClassification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_classification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseClassification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String classification;

    public enum Classifications {

        MA(1L, "MA"),
        ME(2L, "ME"),
        C(3L, "C"),
        NA(4L, "N/A");

        Classifications(long id, String classification) {
            this.id = id;
            this.classification = classification;
        }

        private Long id;
        private String classification;

        public CourseClassification get(){
            return new CourseClassification(id, classification);
        }
    }

}
