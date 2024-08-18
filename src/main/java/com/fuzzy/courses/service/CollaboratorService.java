package com.fuzzy.courses.service;

import com.fuzzy.courses.domain.audit.AuditDto.AuditDto;
import com.fuzzy.courses.domain.audit.AuditDto.AuditDeleteDto;
import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.collaborator.dto.*;
import com.fuzzy.courses.domain.department.Department;
import com.fuzzy.courses.domain.position.Position;
import com.fuzzy.courses.exception.CollaboratorDataAlredyExistsException;
import com.fuzzy.courses.exception.FuzzyNotFoundException;
import com.fuzzy.courses.exception.IdDoesNotExistsException;
import com.fuzzy.courses.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuditRepository auditRepository;

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
        var role = roleRepository.findByName("basic");

        var departmentAdmin = departmentRepository.findByDepartment("Qualidade P&D");

        if (department == departmentAdmin){
            role = roleRepository.findByName("admin");
        }

        var password = passwordEncoder.encode(dto.register());

        return collaboratorRepository.save(dto.toCollaborator(position, department, role, password));

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

    public void updateCollaborator(Long id, UpdateCollaboratorDto dto, JwtAuthenticationToken jwtAuthenticationToken) {

        var user = getCollaborator(jwtAuthenticationToken);

        if(!positionRepository.existsById(dto.positionId())){
            throw new IdDoesNotExistsException("The position ID provided does not exist!");
        }
        if(!departmentRepository.existsById(dto.departmentId())){
            throw new IdDoesNotExistsException("The department ID provided does not exist!");
        }

        var position = positionRepository.getReferenceById(dto.positionId());
        var department = departmentRepository.getReferenceById(dto.departmentId());

        auditUpdate(user, id, dto, position, department);

        collaboratorRepository.findById(id)
                .map(collaborator -> {
                    collaborator.setName(dto.name());
                    collaborator.setPosition(position);
                    collaborator.setDepartment(department);
                    return collaboratorRepository.save(collaborator);
                })
                .orElseThrow(() -> new FuzzyNotFoundException("Collaborator with id " + id + " not found"));
    }

    public void deleteCollaborator(Long id, AuditDeleteDto dto, JwtAuthenticationToken jwtAuthenticationToken) {

        var user = getCollaborator(jwtAuthenticationToken);

        auditDelete(user, id, dto);

        collaboratorRepository.findById(id)
                .map( collaborator -> {
                    collaboratorRepository.delete(collaborator);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new FuzzyNotFoundException("Collaborator with id " + id + " not found"));
    }

    private Collaborator getCollaborator(JwtAuthenticationToken jwtAuthenticationToken) {
        var user = collaboratorRepository.getReferenceById(Long.parseLong(jwtAuthenticationToken.getName()));
        return user;
    }

    private void auditUpdate(Collaborator user, Long id, UpdateCollaboratorDto dto, Position position, Department department) {
        var oldCollaborator = collaboratorRepository.getReferenceById(id);

        List<String> changedField = new ArrayList<>();
        List<String> oldValues = new ArrayList<>();

        if (oldCollaborator.getPosition() != position){
            changedField.add("Position");
            oldValues.add(oldCollaborator.getPosition().getPosition());
        }

        if (oldCollaborator.getDepartment() != department){
            changedField.add("Department");
            oldValues.add(oldCollaborator.getDepartment().getDepartment());
        }

        if (!oldCollaborator.getName().equals(dto.name())){
            changedField.add("Name");
            oldValues.add(oldCollaborator.getName());
        }

        var audit = new AuditDto(user.getName(), null, id ,changedField.toString(), oldValues.toString(), false, null, null);

        auditRepository.save(audit.toAudit(audit));
    }

    private void auditDelete(Collaborator user, Long id, AuditDeleteDto dto) {

        var audit = new AuditDto(user.getName(), null, id ,null, null, true, null, dto.reason());

        auditRepository.save(audit.toAudit(audit));

    }

}
