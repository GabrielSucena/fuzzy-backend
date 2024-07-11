package com.fuzzy.courses.domain.validity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "validities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Validity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String validity;

    public enum Validities {

        DOIS(1L, "2 anos"),
        QUATRO(2L, "4 anos"),
        SEIS(3L, "6 anos");

        Validities(long id, String validity) {
            this.id = id;
            this.validity = validity;
        }

        private Long id;
        private String validity;

        public Validity get(){
            return new Validity(id, validity);
        }
    }

}
