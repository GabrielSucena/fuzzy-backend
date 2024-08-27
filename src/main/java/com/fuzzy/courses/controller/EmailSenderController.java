package com.fuzzy.courses.controller;

import com.fuzzy.courses.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/emailsender")
public class EmailSenderController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/usuario/{id}")
    public ResponseEntity<Void> sendUserEmail(@PathVariable Long id){

        emailService.sendUserEmail(id);

        return ResponseEntity.noContent().build();

    }

    @PostMapping("/curso/{id}")
    public ResponseEntity<Void> sendCourseEmail(@PathVariable Long id){

        emailService.sendCourseEmail(id);

        return ResponseEntity.noContent().build();

    }

}
