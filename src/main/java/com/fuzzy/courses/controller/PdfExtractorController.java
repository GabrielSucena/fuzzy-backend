package com.fuzzy.courses.controller;

import com.fuzzy.courses.controller.dto.ContentResponseDto;
import com.fuzzy.courses.domain.collaborator.dto.UpdateCollaboratorDto;
import com.fuzzy.courses.service.PdfExtractorService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/extracaopdf")
@RequiredArgsConstructor
@Validated
@CrossOrigin("*")
public class PdfExtractorController {

    private final PdfExtractorService pdfExtractorService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContentResponseDto> extractor(@Valid @NotNull @RequestParam("file") final MultipartFile pdfFile){

        return ResponseEntity.ok().body(ContentResponseDto.builder().records(this.pdfExtractorService.extractContent(pdfFile)).build());

    }

    @PatchMapping("{id}")
    @Transactional
    public ResponseEntity<Void> updateStatus (@PathVariable Long id, @RequestBody ContentResponseDto dto){

        pdfExtractorService.updateStatus(id, dto);

        return ResponseEntity.noContent().build();

    }

}
