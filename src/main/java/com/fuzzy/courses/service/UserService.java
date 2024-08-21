package com.fuzzy.courses.service;

import com.fuzzy.courses.controller.dto.DetailUserDto;
import com.fuzzy.courses.controller.dto.UpdateUserDto;
import com.fuzzy.courses.exception.FuzzyNotFoundException;
import com.fuzzy.courses.repository.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public DetailUserDto detailUser(JwtAuthenticationToken jwtAuthenticationToken) {

        var user = collaboratorRepository.getReferenceById(Long.parseLong(jwtAuthenticationToken.getName()));

        return new DetailUserDto(user);

    }

    public void updateUser(UpdateUserDto updateUserDto, JwtAuthenticationToken jwtAuthenticationToken) {

        collaboratorRepository.findById(Long.parseLong(jwtAuthenticationToken.getName()))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(updateUserDto.password()));
                    return collaboratorRepository.save(user);
                }).orElseThrow(() -> new FuzzyNotFoundException("User not found"));

    }
}
