package com.fuzzy.courses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class FuzzyNotFoundException extends FuzzyException{

    private String detail;

    public FuzzyNotFoundException(String detail) {
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pb.setTitle("Collaborator not found.");
        pb.setDetail(detail);

        return pb;
    }

}
