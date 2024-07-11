package com.fuzzy.courses.controller;

import com.fuzzy.courses.domain.collaborator.dto.*;
import com.fuzzy.courses.service.CollaboratorService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/colaboradores")
public class CollaboratorController {

    @Autowired
    private CollaboratorService collaboratorService;

    @PostMapping
    @Transactional
    public ResponseEntity registerCollaborator(@RequestBody @Valid RegisterCollaboratorDto dto, UriComponentsBuilder uriBuilder) {

        var collaborator = collaboratorService.registerCollaborator(dto);

        var uri = uriBuilder.path("/colaboradores/{id}").buildAndExpand(collaborator.getId()).toUri();

        return ResponseEntity.created(uri).body(collaborator);

    }

    @GetMapping
    public ResponseEntity<List<ListCollaboratorsDto>> listCollaborators(){

        var collaborators = collaboratorService.listCollaborators();

        return ResponseEntity.ok(collaborators);

    }

    @GetMapping("{id}")
    public ResponseEntity<DetailCollaboratorDto> detailCollaborator(@PathVariable Long id){

        var collaborator = collaboratorService.detailCollaborator(id);

        return ResponseEntity.ok().body(collaborator);

    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity updateCollaborator(@PathVariable Long id, @RequestBody @Valid UpdateCollaboratorDto dto) {

        collaboratorService.updateCollaborator(id, dto);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity deleteCollaborator(@PathVariable Long id) {

        collaboratorService.deleteCollaborator(id);

        return ResponseEntity.noContent().build();
    }

}
