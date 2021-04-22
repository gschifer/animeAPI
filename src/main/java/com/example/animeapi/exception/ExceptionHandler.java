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

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    //Error Handling for delete method
    @org.springframework.web.bind.annotation.ExceptionHandler({
            EmptyResultDataAccessException.class
    })
    public ResponseEntity errorNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionError("This ID doesn't exist in our data to be removed."));
    }

    //Error Handling for invalid arguments such as ID in a POST method
    @org.springframework.web.bind.annotation.ExceptionHandler({
            IllegalArgumentException.class
    })
    public ResponseEntity errorBadRequest() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionError("You cannot pass an ID as an argument, remove it."));
    }

    //Error Handling for invalid HTTP methods such as POST in an URL like this: http://localhost:8081/api/v1/animes/30
    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                      HttpHeaders headers,
                                                                      HttpStatus status,
                                                                      WebRequest request) {

        return new ResponseEntity<>(new ExceptionError("Operation not allowed."), HttpStatus.METHOD_NOT_ALLOWED);

    }


    //Error Handling for requests that are not allowed such as POST for users, admins are permitted
    @org.springframework.web.bind.annotation.ExceptionHandler({
            AccessDeniedException.class
    })
    public ResponseEntity accessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ExceptionError("Denied access, you don't have permission to do such a thing."));
    }

//    class Error {
//        public String error;
//
//        public Error(String error) {
//            this.error = error;
//        }
//    }
}
