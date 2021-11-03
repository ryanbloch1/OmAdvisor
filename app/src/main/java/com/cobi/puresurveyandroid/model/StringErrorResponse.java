package com.cobi.puresurveyandroid.model;

public class StringErrorResponse {

    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String request;


    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public StringErrorResponse(String error, String request) {
        this.error = error;
        this.request = request;
    }
}
