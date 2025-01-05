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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

    @PostMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_manager')")
    public ResponseEntity<ContentResponseDto> extractor(@PathVariable Long id, @Valid @NotNull @RequestParam("file") final MultipartFile pdfFile){

        var registers = pdfExtractorService.extractContent(id, pdfFile);

        return ResponseEntity.ok(new ContentResponseDto(registers));

    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_manager')")
    @Transactional
    public ResponseEntity<Void> updateStatus (@PathVariable Long id, @RequestBody ContentResponseDto dto, JwtAuthenticationToken jwtAuthenticationToken){

        pdfExtractorService.updateStatus(id, dto, jwtAuthenticationToken);

        return ResponseEntity.noContent().build();

    }

}
