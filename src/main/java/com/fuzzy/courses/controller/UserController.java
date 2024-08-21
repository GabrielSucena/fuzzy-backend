package com.fuzzy.courses.controller;

import com.fuzzy.courses.controller.dto.DetailUserDto;
import com.fuzzy.courses.controller.dto.UpdateUserDto;
import com.fuzzy.courses.domain.collaborator.dto.UpdateCollaboratorDto;
import com.fuzzy.courses.domain.course.dto.DetailCourseDto;
import com.fuzzy.courses.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<DetailUserDto> detailUser(JwtAuthenticationToken jwtAuthenticationToken){

        var user = userService.detailUser(jwtAuthenticationToken);

        return ResponseEntity.ok().body(user);

    }

    @PatchMapping
    @Transactional
    public ResponseEntity<UpdateUserDto> updateUser(@RequestBody @Valid UpdateUserDto updateUserDto, JwtAuthenticationToken jwtAuthenticationToken) {

        userService.updateUser(updateUserDto, jwtAuthenticationToken);

        return ResponseEntity.noContent().build();

    }

}
