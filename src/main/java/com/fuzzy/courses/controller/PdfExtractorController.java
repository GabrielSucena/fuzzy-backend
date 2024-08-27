package com.fuzzy.courses.controller;

import com.fuzzy.courses.controller.dto.ContentResponseDto;
import com.fuzzy.courses.service.PdfExtractorService;
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

    @PostMapping(value = "{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> extractor(@PathVariable Long id, @Valid @NotNull @RequestParam("file") final MultipartFile pdfFile){

        pdfExtractorService.extractContent(id, pdfFile);

        return ResponseEntity.noContent().build();

    }

}
