package com.fuzzy.courses.service;

import com.fuzzy.courses.repository.CollaboratorRepository;
import com.fuzzy.courses.repository.CourseCollaboratorRepository;
import com.fuzzy.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CourseCollaboratorRepository courseCollaboratorRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private CourseRepository courseRepository;

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

        AtomicInteger contador = new AtomicInteger(1);

        var body = "Prezado, " + collaborator.getName() + """
                
                \nEspero que este e-mail te encontre bem.
                
                Gostaríamos de lembrar que você possui alguns cursos pendentes que precisam ser concluídos. A conclusão desses cursos é essencial para o seu desenvolvimento profissional e para o cumprimento das políticas da nossa empresa.
                
                Cursos pendentes: 
                
                """ + courses.stream().map(course -> contador.getAndIncrement() + ". " + course + "\n").collect(Collectors.joining()) +
                """
                
                Pedimos que finalize esses cursos até a data limite. Caso tenha alguma dificuldade ou precise de suporte, por favor, entre em contato conosco para que possamos ajudar.
                
                Agradecemos a sua atenção e colaboração.
                """;

        CompletableFuture.runAsync(() -> sendEmail(collaborator.getEmail(), "Fuzzy Team - Conclusão de Cursos Pendentes", body));

    }

    public void sendCourseEmail(Long id) {

        var collaboratorsId = courseCollaboratorRepository.findUsersWithPending(id);
        var course = courseRepository.getReferenceById(id);

        for(Long collaboratorId : collaboratorsId){
            var collaborator = collaboratorRepository.getReferenceById(collaboratorId);

            var body = "Prezado, " + collaborator.getName() + """
                
                \nEspero que este e-mail te encontre bem. \n""" +
                
                "\nGostaria de lembrá-lo da necessidade de realizar o curso " + course.getTitle().toUpperCase() + """
                . Este treinamento é fundamental para o desempenho de suas atividades, e faz parte dos requisitos que precisamos cumprir dentro dos prazos estabelecidos.
                
                Por gentileza, organize sua agenda para concluir o curso até a Data Limite, garantindo que você esteja plenamente capacitado para as responsabilidades associadas.
                
                Se precisar de algum suporte, ou caso haja algum impedimento para cumprir o prazo, estamos à disposição para ajudar no que for necessário.
                    
                Agradecemos a sua atenção e colaboração.
                """;


            CompletableFuture.runAsync(() -> sendEmail(collaborator.getEmail(), "Fuzzy Team - Pendência de Realização do Curso " + course.getTitle(), body));
        }

    }
}
