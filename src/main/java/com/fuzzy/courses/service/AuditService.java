package com.fuzzy.courses.service;

import com.fuzzy.courses.domain.audit.AuditDto.ListAuditsDto;
import com.fuzzy.courses.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    public List<ListAuditsDto> listAudits() {

        return auditRepository
                .findAll()
                .stream()
                .map(audit -> new ListAuditsDto(audit))
                .collect(Collectors.toList());

    }

    public List<ListAuditsDto> listAuditsByCollaborator(Long id) {

        return auditRepository.findByCollaborator(id);

    }

    public List<ListAuditsDto> listAuditsByCourse(Long id) {

        return auditRepository.findByCourse(id);

    }
}
