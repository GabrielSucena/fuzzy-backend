package com.fuzzy.courses.controller;

import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.course.dto.DetailCourseDto;
import com.fuzzy.courses.domain.course.dto.ListCoursesDto;
import com.fuzzy.courses.domain.course.dto.RegisterCourseDto;
import com.fuzzy.courses.domain.course.dto.UpdateCourseDto;
import com.fuzzy.courses.domain.course.dto.courseCollaborator.AddCollaboratorDto;
import com.fuzzy.courses.domain.course.dto.courseCollaborator.RemoveCollaboratorDto;
import com.fuzzy.courses.domain.course.dto.courseCollaborator.UpdateClassificationAndStatusDto;
import com.fuzzy.courses.service.CourseService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@CrossOrigin("*")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<RegisterCourseDto> registerCourse(@RequestBody @Valid RegisterCourseDto dto, UriComponentsBuilder uriBuilder) {

        var course = courseService.registerCourse(dto);

        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(course.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);

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
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<UpdateCourseDto> updateCourse(@PathVariable Long id, @RequestBody @Valid UpdateCourseDto dto) {

        courseService.updateCourse(id, dto);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("{id}")
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long id) {

        courseService.deleteCourse(id);

        return ResponseEntity.noContent().build();
    }

    // Adicionar/Remover colaboradores vinculados a cursos

    @PostMapping("{id}/colaboradores")
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity addCollaborator(@PathVariable Long id, @RequestBody @Valid AddCollaboratorDto dto){

        courseService.addCollaborator(id, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}/colaboradores")
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity removeCourseCollaborator(@PathVariable Long id, @RequestBody @Valid RemoveCollaboratorDto dto) {

        courseService.removeCourseCollaborator(id, dto);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/colaboradores")
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity updateClassificationAndStatus(@PathVariable Long id, @RequestBody @Valid UpdateClassificationAndStatusDto dto){

        courseService.updateClassificationAndStatus(id, dto);

        return ResponseEntity.noContent().build();
    }


}
