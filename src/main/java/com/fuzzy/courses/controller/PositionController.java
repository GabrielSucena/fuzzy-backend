package com.fuzzy.courses.controller;

import com.fuzzy.courses.domain.position.dto.ListPositionsDto;
import com.fuzzy.courses.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posicoes")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping
    public ResponseEntity<List<ListPositionsDto>> listPositions(){

        var positions = positionService.listPositions();

        return ResponseEntity.ok(positions);

    }

}
