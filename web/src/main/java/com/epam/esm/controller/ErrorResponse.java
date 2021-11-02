package com.epam.esm.controller;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * ResultUtil an object to build an informative
 * exception message
 */
public class ErrorResponse {
    private String errorMsg;
    private String debugMsg;
    private HttpStatus status;
    private List<String> errors;

    public ErrorResponse(String errorMsg, String debugMsh) {
        this.errorMsg = errorMsg;
        this.debugMsg = debugMsh;
    }

    public ErrorResponse(String errorMsg, List<String> errors, HttpStatus status) {
        this.errorMsg = errorMsg;
        this.errors = errors;
    }

    public ErrorResponse(String errorMsg, String debugMsh, HttpStatus status) {
        this.errorMsg = errorMsg;
        this.debugMsg = debugMsh;
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getDebugMsg() {
        return debugMsg;
    }

    public void setDebugMsg(String debugMsg) {
        this.debugMsg = debugMsg;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
