package com.fuzzy.courses.domain.position;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "positions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;

    public enum Positions {

        ANALISTA(1L, "Analista"),
        COORDENADOR(2L, "Coordenador");

        Positions(long id, String position) {
            this.id = id;
            this.position = position;
        }

        private Long id;
        private String position;

        public Position get(){
            return new Position(id, position);
        }
    }

}
