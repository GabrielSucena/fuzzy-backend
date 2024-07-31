package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.codification.Codification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodificationRepository extends JpaRepository<Codification, Long> {

}
