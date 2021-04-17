package com.example.animeapi.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    //Error Handling for delete method
    @org.springframework.web.bind.annotation.ExceptionHandler({
            EmptyResultDataAccessException.class
    })
    public ResponseEntity errorNotFound() {
        return ResponseEntity.notFound().build();
    }

    //Error Handling for invalid arguments such as ID in a POST method
    @org.springframework.web.bind.annotation.ExceptionHandler({
            IllegalArgumentException.class
    })
    public ResponseEntity errorBadRequest() {
        return ResponseEntity.badRequest().build();
    }

    //Error Handling for invalid HTTP methods such as POST in an URL like this: http://localhost:8081/api/v1/animes/30
    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                      HttpHeaders headers,
                                                                      HttpStatus status,
                                                                      WebRequest request) {

        return new ResponseEntity<>(new ExceptionError("Operation not allowed."), HttpStatus.METHOD_NOT_ALLOWED);

    }

    //Shows the message in JSON in the response field
    class ExceptionError implements Serializable {
        private String error;

        ExceptionError(String error) {
            this.error = error;
        }

        //Needed to show the msg in the response field
        public String getError() {
            return error;
        }
    }

    //Error Handling for requests that are not allowed such as POST for users, admins are permitted
    @org.springframework.web.bind.annotation.ExceptionHandler({
            AccessDeniedException.class
    })
    public ResponseEntity accessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Denied Access"));
    }

    class Error {
        public String error;

        public Error(String error) {
            this.error = error;
        }
    }
}
