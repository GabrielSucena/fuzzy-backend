package com.fuzzy.courses.domain.courseCollaborator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fuzzy.courses.domain.collaborator.Collaborator;
import com.fuzzy.courses.domain.course.Course;
import com.fuzzy.courses.domain.courseClassification.CourseClassification;
import com.fuzzy.courses.domain.courseStatus.CourseStatus;
import com.fuzzy.courses.domain.pk.CourseCollaboratorPK;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode
@Entity
@Table(name = "courses_collaborators")
public class CourseCollaborator {

    @EmbeddedId
    private CourseCollaboratorPK id = new CourseCollaboratorPK();

    @ManyToOne
    @JoinColumn(name = "course_classification_id")
    private CourseClassification courseClassification;

    @ManyToOne
    @JoinColumn(name = "course_status_id")
    private CourseStatus courseStatus;

    public CourseCollaborator() {
    }

    public CourseCollaborator(Course course, Collaborator collaborator, CourseClassification courseClassification, CourseStatus courseStatus) {
        id.setCourse(course);
        id.setCollaborator(collaborator);
        this.courseClassification = courseClassification;
        this.courseStatus = courseStatus;
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

    public CourseClassification getCourseClassification() {
        return courseClassification;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseClassification(CourseClassification courseClassification) {
        this.courseClassification = courseClassification;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }
}
