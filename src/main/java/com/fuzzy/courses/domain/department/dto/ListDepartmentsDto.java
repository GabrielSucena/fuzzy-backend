package com.fuzzy.courses.domain.department.dto;

import com.fuzzy.courses.domain.department.Department;

public record ListDepartmentsDto(
        Long id,
        String department
) {
    public ListDepartmentsDto(Department department) {
        this(department.getId(), department.getDepartment());
    }
}
