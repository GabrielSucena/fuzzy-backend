package com.fuzzy.courses.controller;

import com.fuzzy.courses.domain.audit.AuditDto.ListAuditsDto;
import com.fuzzy.courses.domain.collaborator.dto.ListCollaboratorsDto;
import com.fuzzy.courses.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditorias")
@CrossOrigin("*")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<ListAuditsDto>> listAudits(){

        var audits = auditService.listAudits();

        return ResponseEntity.ok(audits);

    }

    @GetMapping("/colaborador/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<ListAuditsDto>> listAuditsByCollaborator(@PathVariable Long id){

        var audits = auditService.listAuditsByCollaborator(id);

        return ResponseEntity.ok(audits);

    }

    @GetMapping("/curso/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<ListAuditsDto>> listAuditsByCourse(@PathVariable Long id){

        var audits = auditService.listAuditsByCourse(id);

        return ResponseEntity.ok(audits);

    }

}
