package com.fuzzy.courses.domain.codification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "codings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Codification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codification;

    public enum Codings {

        CAD(1L, "CAD");

        Codings(long id, String codification) {
            this.id = id;
            this.codification = codification;
        }

        private Long id;
        private String codification;

        public Codification get(){
            return new Codification(id, codification);
        }
    }

}
