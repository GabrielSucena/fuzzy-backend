package com.fuzzy.courses.controller;

import com.fuzzy.courses.domain.course.dto.*;
import com.fuzzy.courses.service.CourseService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Transactional
    public ResponseEntity registerCourse(@RequestBody @Valid RegisterCourseDto dto, UriComponentsBuilder uriBuilder) {

        var course = courseService.registerCourse(dto);

        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(course.getId()).toUri();

        return ResponseEntity.created(uri).body(course);

    }

    @GetMapping
    public ResponseEntity<List<ListCoursesDto>> listCourses(){

        var courses = courseService.listCourses();

        return ResponseEntity.ok(courses);

    }

    @GetMapping("{id}")
    public ResponseEntity<DetailCourseDto> detailCourse(@PathVariable Long id){

        var course = courseService.detailCourse(id);

        return ResponseEntity.ok().body(course);

    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity updateCourse(@PathVariable Long id, @RequestBody @Valid UpdateCourseDto dto) {

        courseService.updateCourse(id, dto);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity deleteCourse(@PathVariable Long id) {

        courseService.deleteCourse(id);

        return ResponseEntity.noContent().build();
    }

    // Adicionar/Remover colaboradores vinculados a cursos

    @PutMapping("/colaboradores/{id}")
    @Transactional
    public ResponseEntity addCollaborator(@PathVariable Long id, @RequestBody @Valid AddCollaboratorDto dto){

        courseService.addCollaborator(id, dto);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/colaboradores/{id}")
    @Transactional
    public ResponseEntity updateClassification(@PathVariable Long id, @RequestBody @Valid UpdateClassificationDto dto){

        courseService.updateClassification(id, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/colaboradores/{id}")
    @Transactional
    public ResponseEntity removeCourseCollaborator(@PathVariable Long id, @RequestBody @Valid RemoveCollaboratorDto dto) {

        courseService.removeCourseCollaborator(id, dto);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/colaboradores/status/{id}")
    @Transactional
    public ResponseEntity updateStatus(@PathVariable Long id, @RequestBody @Valid UpdateStatusDto dto){

        courseService.updateStatus(id, dto);

        return ResponseEntity.noContent().build();
    }
}
