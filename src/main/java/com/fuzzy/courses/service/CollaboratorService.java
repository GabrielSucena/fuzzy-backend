package com.fuzzy.courses.service;

import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.collaborator.dto.*;
import com.fuzzy.courses.exception.CollaboratorDataAlredyExistsException;
import com.fuzzy.courses.exception.FuzzyException;
import com.fuzzy.courses.exception.FuzzyNotFoundException;
import com.fuzzy.courses.repository.CollaboratorRepository;
import com.fuzzy.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollaboratorService {

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Collaborator registerCollaborator(RegisterCollaboratorDto dto) {

        var collaboratorDb = collaboratorRepository.findByCollaboratorRecordOrEmail(dto.collaboratorRecord(), dto.email());
        if (collaboratorDb.isPresent()) {
            throw new CollaboratorDataAlredyExistsException("Record or email alredy exists!");
        }

        return collaboratorRepository.save(dto.toCollaborator());

    }

    public List<ListCollaboratorsDto> listCollaborators() {

        return collaboratorRepository
                .findAll()
                .stream()
                .map(collaborator -> new ListCollaboratorsDto(collaborator))
                .collect(Collectors.toList());

    }

    public DetailCollaboratorDto detailCollaborator(Long id) {

        Optional<Collaborator> collaborator = collaboratorRepository.findById(id);

        return new DetailCollaboratorDto(collaborator.get());

    }


    public void updateCollaborator(Long id, RegisterCollaboratorDto dto) {

        collaboratorRepository.findById(id)
                .map(collaborator -> {
                    collaborator.setFullName(dto.fullName());
                    collaborator.setCollaboratorPosition(dto.collaboratorPosition().get());
                    collaborator.setCollaboratorDepartment(dto.collaboratorDepartment().get());
                    collaborator.setCollaboratorRecord(dto.collaboratorRecord());
                    collaborator.setEmail(dto.email());
                    collaborator.setPhone(dto.phone());
                    return collaboratorRepository.save(collaborator);
                })
                .orElseThrow(() -> new FuzzyNotFoundException("Collaborator with id " + id + " not found"));
    }

    public void deleteCollaborator(Long id) {

        collaboratorRepository.findById(id)
                .map( collaborator -> {
                    collaboratorRepository.delete(collaborator);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new FuzzyNotFoundException("Collaborator with id " + id + " not found"));
    }
}
