package com.example.ftl_project.Exception;

public class ApiProcessingException extends Exception {
    public ApiProcessingException(String message) {
        super(message);
    }

    public ApiProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
