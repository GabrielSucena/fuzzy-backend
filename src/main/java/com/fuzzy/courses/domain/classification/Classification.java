package com.fuzzy.courses.domain.classification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "classifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Classification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "classification")
    private String classification;

}
