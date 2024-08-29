package com.fuzzy.courses.service;

import com.fuzzy.courses.controller.dto.ContentResponseDto;
import com.fuzzy.courses.domain.audit.AuditDto.AuditDto;
import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.courseCollaborator.pk.CourseCollaboratorPK;
import com.fuzzy.courses.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class PdfExtractorService {

    @Autowired
    private CourseCollaboratorRepository courseCollaboratorRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private AuditRepository auditRepository;

    public List<String> extractContent(Long id, MultipartFile multipartFile) {

        String text;
        List<String> registers = new ArrayList<>();

        var course = courseRepository.getReferenceById(id);

        try (final PDDocument document = PDDocument.load(multipartFile.getInputStream())) {
            final PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);

            Pattern pattern = Pattern.compile("\\b\\d{5}\\b");
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {

                var collaborator = collaboratorRepository.findByRegister(matcher.group());

                if(collaborator.isPresent() && courseCollaboratorRepository.findById(new CourseCollaboratorPK(course, collaborator.get())).isPresent()){
                    registers.add(matcher.group());
                }
            }

        } catch (final Exception ex) {
            log.error("Error parsing PDF", ex);
        }

        return registers;

    }

    public void updateStatus(Long id, ContentResponseDto dto, JwtAuthenticationToken jwtAuthenticationToken) {

        var user = getCollaborator(jwtAuthenticationToken);

        for(String register : dto.records()){

            var course = courseRepository.getReferenceById(id);
            var collaborator = collaboratorRepository.findByRegister(register);

            auditalterStatusByRegister(user, id, collaborator.get());

            var courseCollaborator = courseCollaboratorRepository.getReferenceById(new CourseCollaboratorPK(course, collaborator.get()));
            var status = statusRepository.findByStatus("Realizado");

            courseCollaborator.setStatus(status);
            courseCollaborator.setCompletedDate(LocalDate.now());

            courseCollaboratorRepository.save(courseCollaborator);

        }

    }

    // Audits

    private Collaborator getCollaborator(JwtAuthenticationToken jwtAuthenticationToken) {
        var user = collaboratorRepository.getReferenceById(Long.parseLong(jwtAuthenticationToken.getName()));
        return user;
    }

    private void auditalterStatusByRegister(Collaborator user, Long id, Collaborator collaborator) {

        var course = courseRepository.getReferenceById(id);
        var oldCourseCollaborator = courseCollaboratorRepository.getReferenceById(new CourseCollaboratorPK(course, collaborator));

        List<String> changedField = new ArrayList<>();
        List<String> oldValues = new ArrayList<>();

        changedField.add("Status");
        oldValues.add(oldCourseCollaborator.getStatus().getStatus());

        var audit = new AuditDto(user.getName(), id, collaborator.getId(), changedField.toString(), oldValues.toString(), false, course.getVersion(), null);

        auditRepository.save(audit.toAudit(audit));

    }
}
