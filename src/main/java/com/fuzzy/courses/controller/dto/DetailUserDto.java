package com.fuzzy.courses.controller.dto;

import com.fuzzy.courses.domain.collaborator.Collaborator;

public record DetailUserDto(
        Long id,
        String name,
        String register,
        String department,
        String position,
        String email
) {

    public DetailUserDto(Collaborator user){
        this(user.getId(), user.getName(), user.getRegister(), user.getDepartment().getDepartment(), user.getPosition().getPosition(), user.getEmail());
    }

}
