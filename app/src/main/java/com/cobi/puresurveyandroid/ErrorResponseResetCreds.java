package com.cobi.puresurveyandroid;

import com.cobi.puresurveyandroid.model.BaseErrorResponse;

public class ErrorResponseResetCreds extends BaseErrorResponse {
    private String request;

    public ErrorResponseResetCreds() {
    }

    public ErrorResponseResetCreds(String request) {
        this.request = request;
    }
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
