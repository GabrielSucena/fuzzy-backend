package com.fuzzy.courses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class IdDoesNotExistsException extends FuzzyException{

    private String detail;

    public IdDoesNotExistsException(String detail) {
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Id not found");
        pb.setDetail(detail);

        return pb;
    }
}
