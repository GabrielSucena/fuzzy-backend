package com.fuzzy.courses.controller;

import com.fuzzy.courses.domain.audit.AuditDto.ListAuditsDto;
import com.fuzzy.courses.domain.collaborator.dto.ListCollaboratorsDto;
import com.fuzzy.courses.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auditorias")
@CrossOrigin("*")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<ListAuditsDto>> listCollaborators(){

        var audits = auditService.listAudits();

        return ResponseEntity.ok(audits);

    }

}
