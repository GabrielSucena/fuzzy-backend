package com.fuzzy.courses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class FuzzyException extends RuntimeException{

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pb.setTitle("Fuzzy internal server error");

        return pb;
    }

}
