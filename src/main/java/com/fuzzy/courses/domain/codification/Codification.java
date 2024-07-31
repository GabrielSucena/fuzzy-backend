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

    @Column(name = "codification")
    private String codification;

}
