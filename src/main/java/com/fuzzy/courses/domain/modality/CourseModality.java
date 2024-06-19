package com.fuzzy.courses.domain.modality;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_modalities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseModality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modality;

    public enum Modalities {

        ONLINE(1L, "Online"),
        PRESENCIAL(2L, "Presencial");

        Modalities(long id, String modality) {
            this.id = id;
            this.modality = modality;
        }

        private Long id;
        private String modality;

        public CourseModality get(){
            return new CourseModality(id, modality);
        }
    }

}
