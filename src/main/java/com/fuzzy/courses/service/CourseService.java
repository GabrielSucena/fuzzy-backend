package com.fuzzy.courses.service;

import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.course.dto.DetailCourseDto;
import com.fuzzy.courses.domain.course.dto.ListCoursesDto;
import com.fuzzy.courses.domain.course.dto.RegisterCourseDto;
import com.fuzzy.courses.domain.course.dto.UpdateCourseDto;
import com.fuzzy.courses.domain.course.dto.courseCollaborator.AddCollaboratorDto;
import com.fuzzy.courses.domain.course.dto.courseCollaborator.RemoveCollaboratorDto;
import com.fuzzy.courses.domain.course.dto.courseCollaborator.UpdateClassificationAndStatusDto;
import com.fuzzy.courses.domain.courseCollaborator.CourseCollaborator;
import com.fuzzy.courses.domain.courseCollaborator.pk.CourseCollaboratorPK;
import com.fuzzy.courses.exception.FuzzyNotFoundException;
import com.fuzzy.courses.exception.IdDoesNotExistsException;
import com.fuzzy.courses.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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

    public void updateCourse(Long id, UpdateCourseDto dto) {

        courseRepository.findById(id)
                .map(course -> {
                    course.setInstructor(dto.instructor());
                    course.setTitle(dto.title());
                    course.setWorkload(dto.workload());
                    course.setCodification(dto.codification());
                    course.setDescription(dto.description());
                    course.setStartDate(dto.startDate());
                    course.setValidityYears(dto.validityYears());
                    return courseRepository.save(course);
                })
                .orElseThrow(() -> new FuzzyNotFoundException("Course with id " + id + " not found"));

    }

    public void deleteCourse(Long id) {

        courseRepository.findById(id)
                .map( course -> {
                    courseRepository.delete(course);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new FuzzyNotFoundException("Course with id " + id + " not found"));
    }

    // Adicionar/Remover colaboradores vinculados a cursos

    public void addCollaborator(Long id, AddCollaboratorDto dto) {

        if (dto.collaboratorsId() != null){
            for(Long collaboratorId : dto.collaboratorsId()){
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

                    var course = courseRepository.getReferenceById(id);
                    var statusRealizar = statusRepository.getReferenceById(2L);
                    var classificationNA = classificationRepository.getReferenceById(1L);

                    var courseCollaborator = new CourseCollaborator(course, collaborator, classificationNA, statusRealizar);

                    courseCollaboratorRepository.save(courseCollaborator);
                }

            }
        }


    }

    public void removeCourseCollaborator(Long id, RemoveCollaboratorDto dto) {

        if (dto.collaboratorsId() != null) {
            for (Long collaboratorId : dto.collaboratorsId()) {
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
                    var course = courseRepository.getReferenceById(id);

                    var courseCollaborator = courseCollaboratorRepository.getReferenceById(new CourseCollaboratorPK(course, collaborator));

                    courseCollaboratorRepository.delete(courseCollaborator);
                }
            }
        }
    }

    public void updateClassificationAndStatus(Long id, UpdateClassificationAndStatusDto dto) {

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
