package com.fuzzy.courses.service;

import com.fuzzy.courses.domain.department.dto.ListDepartmentsDto;
import com.fuzzy.courses.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<ListDepartmentsDto> listDepartments() {

        return departmentRepository
                .findAll()
                .stream()
                .map(department -> new ListDepartmentsDto(department))
                .collect(Collectors.toList());

    }

}
