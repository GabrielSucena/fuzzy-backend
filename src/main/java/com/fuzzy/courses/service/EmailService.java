package com.fuzzy.courses.service;

import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendTextEmail(Collaborator collaborator, Course course){

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(collaborator.getEmail());
            simpleMailMessage.setSubject("Novo curso adicionado - " + course.getTitle());
            simpleMailMessage.setText("Olá " + collaborator.getFullName() + ". Você foi adicionado a um novo curso chamdo: " + course.getTitle() + ". Você tem até " + course.getDate() + " para realizar este curso. Em caso de dúvidas, estamos a disposição");
            javaMailSender.send(simpleMailMessage);
            return "Emial sent";
        }
        catch (Exception e){
            return "Error when sending email to collaborator " + e.getLocalizedMessage();
        }
    }

}
