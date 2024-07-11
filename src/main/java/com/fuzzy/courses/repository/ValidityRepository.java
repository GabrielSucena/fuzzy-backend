package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.validity.Validity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidityRepository extends JpaRepository<Validity, Long> {
}
