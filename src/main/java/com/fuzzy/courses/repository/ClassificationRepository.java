package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.classification.Classification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassificationRepository extends JpaRepository<Classification, Long> {

}
