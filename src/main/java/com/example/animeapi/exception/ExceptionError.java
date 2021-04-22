package com.example.animeapi.exception;

import java.io.Serializable;

public class ExceptionError implements Serializable {
    private String error;

    public ExceptionError(String error) {
        this.error = error;
    }

    //Needed to show the msg in the response field
    public String getError() {
        return error;
    }
}