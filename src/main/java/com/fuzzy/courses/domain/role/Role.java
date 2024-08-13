package com.fuzzy.courses.domain.role;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public enum Values {

        ADMIN(1L),
        BASIC(2L);

        Long id;

        Values(Long id){
            this.id = id;
        }

        public long getId() {
            return id;
        }

    }

}
