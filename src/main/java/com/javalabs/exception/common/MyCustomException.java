package com.javalabs.exception.common;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


public class MyCustomException extends Exception {
	private static final long serialVersionUID = 1L;
	private final HttpStatus httpStatus;
    private final Error error;

    protected MyCustomException(HttpStatus httpStatus, Error error) {
        this.httpStatus = httpStatus;
        this.error = error;
    }

    public ResponseEntity<Error> getResponse() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        return new ResponseEntity<>(this.error, httpHeaders, this.httpStatus);
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public Error getError() {
        return this.error;
    }
}
