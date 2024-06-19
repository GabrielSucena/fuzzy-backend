package com.fuzzy.courses.config;

import com.fuzzy.courses.domain.department.CollaboratorDepartment;
import com.fuzzy.courses.domain.modality.CourseModality;
import com.fuzzy.courses.domain.position.CollaboratorPosition;
import com.fuzzy.courses.repository.CollaboratorDepartmentRepository;
import com.fuzzy.courses.repository.CollaboratorPositionRepository;
import com.fuzzy.courses.repository.CourseModalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CollaboratorPositionRepository collaboratorPositionRepository;

    @Autowired
    private CollaboratorDepartmentRepository collaboratorDepartmentRepository;

    @Autowired
    private CourseModalityRepository courseModalityRepository;

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(CollaboratorPosition.Positions.values())
                .forEach(collaboratorPosition -> collaboratorPositionRepository.save(collaboratorPosition.get()));

        Arrays.stream(CollaboratorDepartment.Departments.values())
                .forEach(collaboratorDepartments -> collaboratorDepartmentRepository.save(collaboratorDepartments.get()));

        Arrays.stream(CourseModality.Modalities.values())
                .forEach(courseModality -> courseModalityRepository.save(courseModality.get()));
    }

}
