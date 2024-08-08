package com.fuzzy.courses.controller;

import com.fuzzy.courses.domain.department.dto.ListDepartmentsDto;
import com.fuzzy.courses.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
@CrossOrigin("*")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<ListDepartmentsDto>> listDepartments(){

        var departments = departmentService.listDepartments();

        return ResponseEntity.ok(departments);

    }

}
