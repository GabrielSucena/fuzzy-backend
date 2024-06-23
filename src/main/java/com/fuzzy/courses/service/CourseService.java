package com.fuzzy.courses.service;

import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.course.dto.*;
import com.fuzzy.courses.domain.courseCollaborator.CourseCollaborator;
import com.fuzzy.courses.domain.courseStatus.CourseStatus;
import com.fuzzy.courses.domain.pk.CourseCollaboratorPK;
import com.fuzzy.courses.exception.FuzzyException;
import com.fuzzy.courses.exception.FuzzyNotFoundException;
import com.fuzzy.courses.repository.CollaboratorRepository;
import com.fuzzy.courses.repository.CourseCollaboratorRepository;
import com.fuzzy.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private CourseCollaboratorRepository courseCollaboratorRepository;

    @Autowired
    private EmailService emailService;

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

        Optional<Course> course = courseRepository.findById(id);

        return new DetailCourseDto(course.get());

    }

    public void updateCourse(Long id, RegisterCourseDto dto) {

        courseRepository.findById(id)
                .map(course -> {
                    course.setInstructorName(dto.instructorName());
                    course.setVersion(dto.version());
                    course.setTitle(dto.title());
                    course.setWorkload(dto.workload());
                    course.setProcedure(dto.procedure());
                    course.setDescription(dto.description());
                    course.setDate(dto.date());
                    course.setCourseModality(dto.courseModality().get());
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

    public void addCollaborator(Long id, AddCollaboratorDto dto) {

        var collaboratorIsPresent = collaboratorRepository.findById(dto.collaboratorId());

        if (collaboratorIsPresent.isEmpty()){
            throw new FuzzyNotFoundException("Collaborator with id " + dto.collaboratorId() + " not found");
        }

        var course = courseRepository.getReferenceById(id);
        var collaborator = collaboratorRepository.getReferenceById(dto.collaboratorId());

        var courseCollaborator = new CourseCollaborator(course, collaborator, null, CourseStatus.Status.A_REALIZAR.get());

        courseCollaboratorRepository.save(courseCollaborator);

        emailService.sendTextEmail(collaborator, course);

    }

    public void removeCourseCollaborator(Long id, RemoveCollaboratorDto dto) {
        var collaboratorIsPresent = collaboratorRepository.findById(dto.collaboratorId());

        if (collaboratorIsPresent.isEmpty()){
            throw new FuzzyNotFoundException("Collaborator with id " + dto.collaboratorId() + " not found");
        }

        var course = courseRepository.getReferenceById(id);
        var collaborator = collaboratorRepository.getReferenceById(dto.collaboratorId());

        var courseCollaborator = courseCollaboratorRepository.getReferenceById(new CourseCollaboratorPK(course, collaborator));

        courseCollaboratorRepository.delete(courseCollaborator);
    }

    public void updateStatus(Long id, UpdateStatusDto dto) {

        var collaboratorIsPresent = collaboratorRepository.findById(dto.collaboratorId());

        if (collaboratorIsPresent.isEmpty()){
            throw new FuzzyNotFoundException("Collaborator with id " + dto.collaboratorId() + " not found");
        }

        var course = courseRepository.getReferenceById(id);
        var collaborator = collaboratorRepository.getReferenceById(dto.collaboratorId());

        var courseCollaborator = courseCollaboratorRepository.getReferenceById(new CourseCollaboratorPK(course, collaborator));

        if (Objects.equals(courseCollaborator.getCourseStatus().getStatus(), CourseStatus.Status.REALIZADO.get().getStatus())){
            courseCollaborator.setCourseStatus(CourseStatus.Status.A_REALIZAR.get());
        }else {
            courseCollaborator.setCourseStatus(CourseStatus.Status.REALIZADO.get());
        }

        courseCollaboratorRepository.save(courseCollaborator);
    }

    public void updateClassification(Long id, UpdateClassificationDto dto) {

        var collaboratorIsPresent = collaboratorRepository.findById(dto.collaboratorId());

        if (collaboratorIsPresent.isEmpty()){
            throw new FuzzyNotFoundException("Collaborator with id " + dto.collaboratorId() + " not found");
        }

        var course = courseRepository.getReferenceById(id);
        var collaborator = collaboratorRepository.getReferenceById(dto.collaboratorId());

        var courseCollaborator = courseCollaboratorRepository.getReferenceById(new CourseCollaboratorPK(course, collaborator));

        courseCollaborator.setCourseClassification(dto.classifications().get());

        courseCollaboratorRepository.save(courseCollaborator);

    }
}
