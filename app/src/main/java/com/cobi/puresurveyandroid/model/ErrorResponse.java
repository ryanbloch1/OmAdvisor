package com.cobi.puresurveyandroid.model;

import retrofit2.Response;

/**
 * Created by admin on 2017/10/04.
 */

public class ErrorResponse extends BaseResponse {

    private String request;

    public ErrorResponse() {
    }

    public ErrorResponse(String request) {
        this.request = request;
    }


    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
