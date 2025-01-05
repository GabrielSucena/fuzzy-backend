package com.fuzzy.courses.controller;

import com.fuzzy.courses.domain.audit.AuditDto.AuditDeleteDto;
import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.course.dto.DetailCourseDto;
import com.fuzzy.courses.domain.course.dto.ListCoursesDto;
import com.fuzzy.courses.domain.course.dto.RegisterCourseDto;
import com.fuzzy.courses.domain.course.dto.UpdateCourseDto;
import com.fuzzy.courses.domain.course.dto.courseCollaborator.*;
import com.fuzzy.courses.service.CourseService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
    @PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_manager')")
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
    @PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_manager')")
    public ResponseEntity<UpdateCourseDto> updateCourse(@PathVariable Long id, @RequestBody @Valid UpdateCourseDto dto, JwtAuthenticationToken jwtAuthenticationToken) {

        courseService.updateCourse(id, dto, jwtAuthenticationToken);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("{id}")
    @Transactional
    @PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_manager')")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long id, @RequestBody @Valid AuditDeleteDto dto, JwtAuthenticationToken jwtAuthenticationToken) {

        courseService.deleteCourse(id, dto, jwtAuthenticationToken);

        return ResponseEntity.noContent().build();
    }

    // Add/Remove collaborators linked to courses

    @PostMapping("{id}/colaboradores")
    @Transactional
    @PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_manager')")
    public ResponseEntity addCollaborator(@PathVariable Long id, @RequestBody @Valid AddCollaboratorDto dto, JwtAuthenticationToken jwtAuthenticationToken){

        courseService.addCollaborator(id, dto, jwtAuthenticationToken);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}/colaboradores")
    @Transactional
    @PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_manager')")
    public ResponseEntity removeCourseCollaborator(@PathVariable Long id, @RequestBody @Valid RemoveCollaboratorDto dto, JwtAuthenticationToken jwtAuthenticationToken) {

        courseService.removeCourseCollaborator(id, dto, jwtAuthenticationToken);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/colaboradores")
    @Transactional
    @PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_manager')")
    public ResponseEntity updateClassificationAndStatus(@PathVariable Long id, @RequestBody @Valid ListUpdateClassificationAndStatusDto dtoList, JwtAuthenticationToken jwtAuthenticationToken){

        courseService.updateClassificationAndStatus(id, dtoList, jwtAuthenticationToken);

        return ResponseEntity.noContent().build();
    }

}
