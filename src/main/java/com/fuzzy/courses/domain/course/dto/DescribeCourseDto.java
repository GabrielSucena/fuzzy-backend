package com.fuzzy.courses.domain.course.dto;

import java.time.LocalDate;

public interface DescribeCourseDto {
    Long getCourse_id();
    LocalDate getEnd_date();
    String getStatus();
    Long getGreen();
    Long getYellow();
    Long getOrange();
    Long getRed();
    Long getNa();
    Long getMenor();
    Long getMaior();
    Long getCritico();
    Long getQuantidade();
}
