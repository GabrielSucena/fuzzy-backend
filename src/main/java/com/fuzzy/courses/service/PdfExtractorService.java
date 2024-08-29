package com.fuzzy.courses.service;

import com.fuzzy.courses.controller.dto.ContentResponseDto;
import com.fuzzy.courses.domain.courseCollaborator.pk.CourseCollaboratorPK;
import com.fuzzy.courses.repository.CollaboratorRepository;
import com.fuzzy.courses.repository.CourseCollaboratorRepository;
import com.fuzzy.courses.repository.CourseRepository;
import com.fuzzy.courses.repository.StatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public List<String> extractContent(MultipartFile multipartFile) {

        String text;
        List<String> registers = new ArrayList<>();

        try (final PDDocument document = PDDocument.load(multipartFile.getInputStream())) {
            final PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);

            Pattern pattern = Pattern.compile("\\b\\d{5}\\b");
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                if(collaboratorRepository.findByRegister(matcher.group()).isPresent()){
                    registers.add(matcher.group());
                }
            }

        } catch (final Exception ex) {
            log.error("Error parsing PDF", ex);
        }

        return registers;

    }

    public void updateStatus(Long id, ContentResponseDto dto) {

        for(String register : dto.records()){

            var course = courseRepository.getReferenceById(id);
            var collaborator = collaboratorRepository.findByRegister(register);
            var courseCollaborator = courseCollaboratorRepository.getReferenceById(new CourseCollaboratorPK(course, collaborator.get()));
            var status = statusRepository.findByStatus("Realizado");

            courseCollaborator.setStatus(status);

            courseCollaboratorRepository.save(courseCollaborator);

        }

    }
}
