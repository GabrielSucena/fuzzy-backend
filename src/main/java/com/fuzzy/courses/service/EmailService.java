package com.fuzzy.courses.service;

import com.fuzzy.courses.repository.CollaboratorRepository;
import com.fuzzy.courses.repository.CourseCollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CourseCollaboratorRepository courseCollaboratorRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Value("$spring.mail.username")
    private String from;

    public void sendEmail(String to, String subject, String body){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

    }

    public void sendUserEmail(Long id) {

        var courses = courseCollaboratorRepository.findUserCourses(id);

        var collaborator = collaboratorRepository.getReferenceById(id);

        sendEmail(collaborator.getEmail(), "Teste de email", courses.toString());

    }

    public void sendCourseEmail(Long id) {

        var collaborators = courseCollaboratorRepository.findUsersWithPending(id);

        for(String collaborator : collaborators){
            sendEmail(collaborator, "Teste de email", "Realizar este curso de id " + id);
        }

    }
}
