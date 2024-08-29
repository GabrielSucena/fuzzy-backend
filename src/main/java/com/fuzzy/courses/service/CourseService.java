package com.fuzzy.courses.service;

import com.fuzzy.courses.domain.audit.AuditDto.AuditDeleteDto;
import com.fuzzy.courses.domain.audit.AuditDto.AuditDto;
import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.course.dto.DetailCourseDto;
import com.fuzzy.courses.domain.course.dto.ListCoursesDto;
import com.fuzzy.courses.domain.course.dto.RegisterCourseDto;
import com.fuzzy.courses.domain.course.dto.UpdateCourseDto;
import com.fuzzy.courses.domain.course.dto.courseCollaborator.*;
import com.fuzzy.courses.domain.courseCollaborator.CourseCollaborator;
import com.fuzzy.courses.domain.courseCollaborator.pk.CourseCollaboratorPK;
import com.fuzzy.courses.exception.FuzzyNotFoundException;
import com.fuzzy.courses.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ClassificationRepository classificationRepository;

    @Autowired
    private CourseCollaboratorRepository courseCollaboratorRepository;

    @Autowired
    private AuditRepository auditRepository;

    public Course registerCourse(RegisterCourseDto dto) {

        return courseRepository.save(dto.toCourse());

    }

    public List<ListCoursesDto> listCourses() {

        return courseRepository
                .findAll()
                .stream()
                .map(course -> new ListCoursesDto(course))
                .collect(Collectors.toList());
    }

    public DetailCourseDto detailCourse(Long id) {

        var course = courseRepository.findById(id);

        var collaborators = courseCollaboratorRepository.listCollaborators(id);

        LocalDate today = LocalDate.now();
        var description = courseRepository.describeCourse(id, today);

        return new DetailCourseDto(course.get(), collaborators, description);

    }

    public void updateCourse(Long id, UpdateCourseDto dto, JwtAuthenticationToken jwtAuthenticationToken) {

        var user = getCollaborator(jwtAuthenticationToken);

        auditUpdate(user, id, dto);

        courseRepository.findById(id)
                .map(course -> {
                    course.setInstructor(dto.instructor());
                    course.setTitle(dto.title());
                    course.setWorkload(dto.workload());
                    course.setCodification(dto.codification());
                    course.setDescription(dto.description());
                    course.setStartDate(dto.startDate());
                    course.setValidityYears(dto.validityYears());
                    course.setVersion(String.valueOf(Long.sum(Long.parseLong(course.getVersion()), 1)));
                    return courseRepository.save(course);
                })
                .orElseThrow(() -> new FuzzyNotFoundException("Course with id " + id + " not found"));

    }

    public void deleteCourse(Long id, AuditDeleteDto dto, JwtAuthenticationToken jwtAuthenticationToken) {

        var user = getCollaborator(jwtAuthenticationToken);

        auditDelete(user, id, dto);

        courseRepository.findById(id)
                .map( course -> {
                    courseRepository.delete(course);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new FuzzyNotFoundException("Course with id " + id + " not found"));
    }

    // Adicionar/Remover colaboradores vinculados a cursos

    public void addCollaborator(Long id, AddCollaboratorDto dto, JwtAuthenticationToken jwtAuthenticationToken) {

        var user = getCollaborator(jwtAuthenticationToken);

        if (dto.collaboratorsId() != null){
            for(Long collaboratorId : dto.collaboratorsId()){

                auditAddCollaboratorCourse(user, id, collaboratorId);

                var collaboratorIsPresent = collaboratorRepository.findById(collaboratorId);

                if (collaboratorIsPresent.isEmpty()){
                    throw new FuzzyNotFoundException("Collaborator with id " + collaboratorId + " not found");
                }

                var course = courseRepository.getReferenceById(id);
                var collaborator = collaboratorRepository.getReferenceById(collaboratorId);
                var statusRealizar = statusRepository.getReferenceById(2L);
                var classificationNA = classificationRepository.getReferenceById(1L);

                var courseCollaborator = new CourseCollaborator(course, collaborator, classificationNA, statusRealizar);

                courseCollaboratorRepository.save(courseCollaborator);
            }
        }

        if (dto.departmentsId() != null){
            for(Long departmentId : dto.departmentsId()){

                var collaborators = collaboratorRepository.findByDepartment_id(departmentId);

                for(Collaborator collaborator : collaborators){

                    auditAddCollaboratorCourse(user, id, collaborator.getId());

                    var course = courseRepository.getReferenceById(id);
                    var statusRealizar = statusRepository.getReferenceById(2L);
                    var classificationNA = classificationRepository.getReferenceById(1L);

                    var courseCollaborator = new CourseCollaborator(course, collaborator, classificationNA, statusRealizar);

                    courseCollaboratorRepository.save(courseCollaborator);
                }

            }
        }


    }

    public void removeCourseCollaborator(Long id, RemoveCollaboratorDto dto, JwtAuthenticationToken jwtAuthenticationToken) {

        var user = getCollaborator(jwtAuthenticationToken);

        if (dto.collaboratorsId() != null) {
            for (Long collaboratorId : dto.collaboratorsId()) {

                auditDeleteCollaboratorCourse(user, id, collaboratorId);

                var collaboratorIsPresent = collaboratorRepository.findById(collaboratorId);

                if (collaboratorIsPresent.isEmpty()){
                    throw new FuzzyNotFoundException("Collaborator with id " + collaboratorId + " not found");
                }

                var course = courseRepository.getReferenceById(id);
                var collaborator = collaboratorRepository.getReferenceById(collaboratorId);

                var courseCollaborator = courseCollaboratorRepository.getReferenceById(new CourseCollaboratorPK(course, collaborator));

                courseCollaboratorRepository.delete(courseCollaborator);
            }
        }

        if (dto.departmentsId() != null) {
            for (Long departmentId : dto.departmentsId()) {
                var collaborators = collaboratorRepository.findByDepartment_id(departmentId);

                for(Collaborator collaborator : collaborators){

                    auditDeleteCollaboratorCourse(user, id, collaborator.getId());

                    var course = courseRepository.getReferenceById(id);

                    var courseCollaborator = courseCollaboratorRepository.getReferenceById(new CourseCollaboratorPK(course, collaborator));

                    courseCollaboratorRepository.delete(courseCollaborator);
                }
            }
        }
    }

    public void updateClassificationAndStatus(Long id, ListUpdateClassificationAndStatusDto dtoList, JwtAuthenticationToken jwtAuthenticationToken) {

        var user = getCollaborator(jwtAuthenticationToken);

        for(UpdateClassificationAndStatusDto dto : dtoList.updateClassificationAndStatusDtoList()){
            auditUpdateCollaboratorCourse(user, id, dto);

            var collaboratorIsPresent = collaboratorRepository.findById(dto.collaboratorId());

            if (collaboratorIsPresent.isEmpty()){
                throw new FuzzyNotFoundException("Collaborator with id " + dto.collaboratorId() + " not found");
            }

            var course = courseRepository.getReferenceById(id);
            var collaborator = collaboratorRepository.getReferenceById(dto.collaboratorId());

            var courseCollaborator = courseCollaboratorRepository.getReferenceById(new CourseCollaboratorPK(course, collaborator));

            if (dto.classificationId() != null) {
                var classification = classificationRepository.getReferenceById(dto.classificationId());
                courseCollaborator.setClassification(classification);
            }
            if (dto.statusId() != null) {
                var status = statusRepository.getReferenceById(dto.statusId());
                courseCollaborator.setStatus(status);
                if (status.getId() == 1){
                    courseCollaborator.setCompletedDate(LocalDate.now());
                } else {
                    courseCollaborator.setCompletedDate(null);
                }
            }


            courseCollaboratorRepository.save(courseCollaborator);
        }

    }

    public void alterStatusByRegister(Long id, AlterStatusByRegisterDto dto, JwtAuthenticationToken jwtAuthenticationToken) {

        var user = getCollaborator(jwtAuthenticationToken);
        var course = courseRepository.getReferenceById(id);

        for(String register : dto.registers()) {
            var collaborator = collaboratorRepository.findByRegister(register);

            if (collaborator.isEmpty()){
                throw new FuzzyNotFoundException("Collaborator with register " + register + " not found");
            }

            auditalterStatusByRegister(user, id, collaborator.get());

            var courseCollaborator = courseCollaboratorRepository.getReferenceById(new CourseCollaboratorPK(course, collaborator.get()));
            var status = statusRepository.findByStatus("Realizado");

            courseCollaborator.setStatus(status);
            courseCollaborator.setCompletedDate(LocalDate.now());

            courseCollaboratorRepository.save(courseCollaborator);

        }

    }

    // Audits

    private Collaborator getCollaborator(JwtAuthenticationToken jwtAuthenticationToken) {
        var user = collaboratorRepository.getReferenceById(Long.parseLong(jwtAuthenticationToken.getName()));
        return user;
    }

    private void auditUpdate(Collaborator user, Long id, UpdateCourseDto dto) {

        var oldCourse = courseRepository.getReferenceById(id);

        List<String> changedField = new ArrayList<>();
        List<String> oldValues = new ArrayList<>();

        if(!oldCourse.getInstructor().equals(dto.instructor())){
            changedField.add("Instrutor");
            oldValues.add(oldCourse.getInstructor());
        }

        if(!oldCourse.getTitle().equals(dto.title())){
            changedField.add("Título");
            oldValues.add(oldCourse.getTitle());
        }

        if(!oldCourse.getWorkload().equals(dto.workload())){
            changedField.add("Carga Horária");
            oldValues.add(oldCourse.getWorkload());
        }

        if(!oldCourse.getCodification().equals(dto.codification())){
            changedField.add("Codificação");
            oldValues.add(oldCourse.getCodification());
        }

        if(!oldCourse.getDescription().equals(dto.description())){
            changedField.add("Descrição");
            oldValues.add(oldCourse.getDescription());
        }

        if(!oldCourse.getStartDate().equals(dto.startDate())){
            changedField.add("Data de Início");
            oldValues.add(oldCourse.getStartDate().toString());
        }

        if(!Objects.equals(oldCourse.getValidityYears(), dto.validityYears())){
            changedField.add("Validade");
            oldValues.add(oldCourse.getValidityYears().toString());
        }

        var audit = new AuditDto(user.getName(), id, null,changedField.toString(), oldValues.toString(), false, oldCourse.getVersion(), null);

        auditRepository.save(audit.toAudit(audit));

    }

    private void auditDelete(Collaborator user, Long id, AuditDeleteDto dto) {

        var oldCourse = courseRepository.getReferenceById(id);

        var audit = new AuditDto(user.getName(), id, null ,null, null, true, oldCourse.getVersion(), dto.reason());

        auditRepository.save(audit.toAudit(audit));

    }

    private void auditAddCollaboratorCourse(Collaborator user, Long id, Long collaboratorId) {

        var course = courseRepository.getReferenceById(id);

        var audit = new AuditDto(user.getName(), id, collaboratorId , null, null, false, course.getVersion(), null);

        auditRepository.save(audit.toAudit(audit));

    }

    private void auditDeleteCollaboratorCourse(Collaborator user, Long id, Long collaboratorId) {

        var course = courseRepository.getReferenceById(id);

        var audit = new AuditDto(user.getName(), id, collaboratorId , null, null, true, course.getVersion(), null);

        auditRepository.save(audit.toAudit(audit));

    }

    private void auditUpdateCollaboratorCourse(Collaborator user, Long id, UpdateClassificationAndStatusDto dto) {

        var course = courseRepository.getReferenceById(id);
        var collaborator = collaboratorRepository.getReferenceById(dto.collaboratorId());

        var oldCourseCollaborator = courseCollaboratorRepository.getReferenceById(new CourseCollaboratorPK(course, collaborator));

        List<String> changedField = new ArrayList<>();
        List<String> oldValues = new ArrayList<>();

        if(dto.classificationId() != null && !Objects.equals(oldCourseCollaborator.getClassification().getId(), dto.classificationId())){
            changedField.add("Classificação");
            oldValues.add(oldCourseCollaborator.getClassification().getClassification());
        }

        if(dto.statusId() != null && !Objects.equals(oldCourseCollaborator.getStatus().getId(), dto.statusId())){
            changedField.add("Status");
            oldValues.add(oldCourseCollaborator.getStatus().getStatus());
        }

        var audit = new AuditDto(user.getName(), id, dto.collaboratorId(), changedField.toString(), oldValues.toString(), false, course.getVersion(), null);

        auditRepository.save(audit.toAudit(audit));
    }

    private void auditalterStatusByRegister(Collaborator user, Long id, Collaborator collaborator) {

        var course = courseRepository.getReferenceById(id);
        var oldCourseCollaborator = courseCollaboratorRepository.getReferenceById(new CourseCollaboratorPK(course, collaborator));

        List<String> changedField = new ArrayList<>();
        List<String> oldValues = new ArrayList<>();

        changedField.add("Status");
        oldValues.add(oldCourseCollaborator.getStatus().getStatus());

        var audit = new AuditDto(user.getName(), id, collaborator.getId(), changedField.toString(), oldValues.toString(), false, course.getVersion(), null);

        auditRepository.save(audit.toAudit(audit));

    }
}
