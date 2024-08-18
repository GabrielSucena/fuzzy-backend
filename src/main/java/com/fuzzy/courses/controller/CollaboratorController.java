package com.fuzzy.courses.controller;

import com.fuzzy.courses.domain.audit.AuditDto.AuditDeleteDto;
import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.collaborator.dto.*;
import com.fuzzy.courses.service.CollaboratorService;
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
@RequestMapping("/colaboradores")
@CrossOrigin("*")
public class CollaboratorController {

    @Autowired
    private CollaboratorService collaboratorService;

    @PostMapping
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<RegisterCollaboratorDto> registerCollaborator(@RequestBody @Valid RegisterCollaboratorDto dto, UriComponentsBuilder uriBuilder) {

        var collaborator = collaboratorService.registerCollaborator(dto);

        var uri = uriBuilder.path("/colaboradores/{id}").buildAndExpand(collaborator.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);

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
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<UpdateCollaboratorDto> updateCollaborator(@PathVariable Long id, @RequestBody @Valid UpdateCollaboratorDto dto, JwtAuthenticationToken jwtAuthenticationToken) {

        collaboratorService.updateCollaborator(id, dto, jwtAuthenticationToken);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("{id}")
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Collaborator> deleteCollaborator(@PathVariable Long id, JwtAuthenticationToken jwtAuthenticationToken, @RequestBody @Valid AuditDeleteDto dto) {

        collaboratorService.deleteCollaborator(id, dto, jwtAuthenticationToken);

        return ResponseEntity.noContent().build();
    }

}
