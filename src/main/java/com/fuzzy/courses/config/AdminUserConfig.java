package com.fuzzy.courses.config;

import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.repository.CollaboratorRepository;
import com.fuzzy.courses.repository.DepartmentRepository;
import com.fuzzy.courses.repository.PositionRepository;
import com.fuzzy.courses.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName("admin");
        var userAdmin = collaboratorRepository.findByName("admin");
        var position = positionRepository.getReferenceById(3L);
        var department = departmentRepository.getReferenceById(3L);


        userAdmin.ifPresentOrElse(
                (collaborator) -> {
                    System.out.println("admin já existe!");
                },
                () -> {
                    var collaborator = new Collaborator();
                    collaborator.setName("admin");
                    collaborator.setRegister("admin");
                    collaborator.setPosition(position);
                    collaborator.setDepartment(department);
                    collaborator.setPassword(bCryptPasswordEncoder.encode("admin"));
                    collaborator.setRoles(Set.of(roleAdmin));
                    collaboratorRepository.save(collaborator);
                }
        );



    }
}
