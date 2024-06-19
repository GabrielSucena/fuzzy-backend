package com.fuzzy.courses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class CollaboratorDataAlredyExistsException extends FuzzyException{

    private String detail;

    public CollaboratorDataAlredyExistsException(String detail) {
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Collaborator data alredy exists");
        pb.setDetail(detail);

        return pb;
    }

}
