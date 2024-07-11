package com.fuzzy.courses.config;

import com.fuzzy.courses.domain.codification.Codification;
import com.fuzzy.courses.domain.courseClassification.CourseClassification;
import com.fuzzy.courses.domain.courseStatus.CourseStatus;
import com.fuzzy.courses.domain.department.Department;
import com.fuzzy.courses.domain.modality.Modality;
import com.fuzzy.courses.domain.position.Position;
import com.fuzzy.courses.domain.validity.Validity;
import com.fuzzy.courses.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModalityRepository modalityRepository;

    @Autowired
    private CourseClassificationRepository classificationRepository;

    @Autowired
    private CourseStatusRepository statusRepository;

    @Autowired
    private CodificationRepository codificationRepository;

    @Autowired
    private ValidityRepository validityRepository;

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(Position.Positions.values())
                .forEach(position -> positionRepository.save(position.get()));

        Arrays.stream(Department.Departments.values())
                .forEach(departments -> departmentRepository.save(departments.get()));

        Arrays.stream(Modality.Modalities.values())
                .forEach(modality -> modalityRepository.save(modality.get()));

        Arrays.stream(CourseClassification.Classifications.values())
                .forEach(courseClassification -> classificationRepository.save(courseClassification.get()));

        Arrays.stream(CourseStatus.Status.values())
                .forEach(courseStatus -> statusRepository.save(courseStatus.get()));

        Arrays.stream(Codification.Codings.values())
                .forEach(codings -> codificationRepository.save(codings.get()));

        Arrays.stream(Validity.Validities.values())
                .forEach(validities -> validityRepository.save(validities.get()));

    }

}
