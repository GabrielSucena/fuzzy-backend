package com.fuzzy.courses.service;

import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.course.dto.*;
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
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

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

        var collaborator = collaboratorRepository.getReferenceById(dto.collaboratorId());

        courseRepository.findById(id)
                .map( course -> {
                    course.getCollaborators().add(collaborator);
                    return courseRepository.save(course);
                })
                .orElseThrow( () -> new FuzzyException());

    }

    public void removeCourseCollaborator(Long id, RemoveCollaboratorDto dto) {

        var collaboratorIsPresent = collaboratorRepository.findById(dto.collaboratorId());

        if (collaboratorIsPresent.isEmpty()){
            throw new FuzzyNotFoundException("Collaborator with id " + dto.collaboratorId() + " not found");
        }

        var collaborator = collaboratorRepository.getReferenceById(dto.collaboratorId());

        courseRepository.findById(id)
                .map( course -> {
                    course.getCollaborators().remove(collaborator);
                    return courseRepository.save(course);
                })
                .orElseThrow(() -> new FuzzyException());
    }
}
