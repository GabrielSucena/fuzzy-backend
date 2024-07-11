package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.modality.Modality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModalityRepository extends JpaRepository<Modality, Long> {
}
