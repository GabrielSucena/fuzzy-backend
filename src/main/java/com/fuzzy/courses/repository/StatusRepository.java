package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {

    Status findByStatus(String realizado);

}
