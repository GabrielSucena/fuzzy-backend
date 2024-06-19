package com.fuzzy.courses.repository;

import com.fuzzy.courses.domain.collaborator.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {

    Optional<Collaborator> findByCollaboratorRecordOrEmail(String collaboratorRecord, String email);

}
