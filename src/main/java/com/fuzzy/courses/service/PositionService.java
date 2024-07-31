package com.fuzzy.courses.service;

import com.fuzzy.courses.domain.position.dto.ListPositionsDto;
import com.fuzzy.courses.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    public List<ListPositionsDto> listPositions() {
        return positionRepository
                .findAll()
                .stream()
                .map(position -> new ListPositionsDto(position))
                .collect(Collectors.toList());
    }

}
