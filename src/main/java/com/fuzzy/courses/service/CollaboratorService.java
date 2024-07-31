package com.fuzzy.courses.service;

import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.collaborator.dto.DetailCollaboratorDto;
import com.fuzzy.courses.domain.collaborator.dto.ListCollaboratorsDto;
import com.fuzzy.courses.domain.collaborator.dto.RegisterCollaboratorDto;
import com.fuzzy.courses.domain.collaborator.dto.UpdateCollaboratorDto;
import com.fuzzy.courses.exception.CollaboratorDataAlredyExistsException;
import com.fuzzy.courses.exception.FuzzyNotFoundException;
import com.fuzzy.courses.exception.IdDoesNotExistsException;
import com.fuzzy.courses.repository.CollaboratorRepository;
import com.fuzzy.courses.repository.CourseCollaboratorRepository;
import com.fuzzy.courses.repository.DepartmentRepository;
import com.fuzzy.courses.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollaboratorService {

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private CourseCollaboratorRepository courseCollaboratorRepository;

    public Collaborator registerCollaborator(RegisterCollaboratorDto dto) {

        var collaborator = collaboratorRepository.findByRegisterOrEmail(dto.register(), dto.email());
        if (collaborator.isPresent()) {
            throw new CollaboratorDataAlredyExistsException("Record or email alredy exists!");
        }

        if(!positionRepository.existsById(dto.positionId())){
            throw new IdDoesNotExistsException("The position ID provided does not exist!");
        }
        if(!departmentRepository.existsById(dto.departmentId())){
            throw new IdDoesNotExistsException("The department ID provided does not exist!");
        }

        var position = positionRepository.getReferenceById(dto.positionId());
        var department = departmentRepository.getReferenceById(dto.departmentId());

        return collaboratorRepository.save(dto.toCollaborator(position, department));

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

        var courses = courseCollaboratorRepository.listCourses(id);

        LocalDate today = LocalDate.now();
        var description = collaboratorRepository.describeCollaborator(id, today);

        return new DetailCollaboratorDto(collaborator.get(), courses, description);

    }

    public void updateCollaborator(Long id, UpdateCollaboratorDto dto) {

        if(!positionRepository.existsById(dto.positionId())){
            throw new IdDoesNotExistsException("The position ID provided does not exist!");
        }
        if(!departmentRepository.existsById(dto.departmentId())){
            throw new IdDoesNotExistsException("The department ID provided does not exist!");
        }

        var position = positionRepository.getReferenceById(dto.positionId());
        var department = departmentRepository.getReferenceById(dto.departmentId());

        collaboratorRepository.findById(id)
                .map(collaborator -> {
                    collaborator.setName(dto.name());
                    collaborator.setPosition(position);
                    collaborator.setDepartment(department);
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
