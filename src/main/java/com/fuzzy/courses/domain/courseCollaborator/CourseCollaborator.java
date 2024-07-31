package com.fuzzy.courses.domain.courseCollaborator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fuzzy.courses.domain.classification.Classification;
import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.courseCollaborator.pk.CourseCollaboratorPK;
import com.fuzzy.courses.domain.status.Status;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@EqualsAndHashCode
@Entity
@Table(name = "courses_collaborators")
public class CourseCollaborator {

    @EmbeddedId
    private CourseCollaboratorPK id = new CourseCollaboratorPK();

    @ManyToOne
    @JoinColumn(name = "classification_id")
    private Classification classification;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @JoinColumn(name = "completed_date")
    private LocalDate completedDate;

    public CourseCollaborator() {
    }

    public CourseCollaborator(Course course, Collaborator collaborator, Classification classification, Status status) {
        id.setCourse(course);
        id.setCollaborator(collaborator);
        this.classification = classification;
        this.status = status;
    }

    public CourseCollaborator(Course course, Collaborator collaborator) {
        id.setCourse(course);
        id.setCollaborator(collaborator);
    }

    @JsonIgnore
    public Course getCourse() {
        return id.getCourse();
    }

    public void setCourse(Course course){
        id.setCourse(course);
    }

    public Collaborator getCollaborator(){
        return id.getCollaborator();
    }

    public void setCollaborator(Collaborator collaborator){
        id.setCollaborator(collaborator);
    }

    public Classification getClassification() {
        return classification;
    }

    public Status getStatus() {
        return status;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }
}
