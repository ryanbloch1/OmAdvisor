package com.cobi.puresurveyandroid.model;

/**
 * Created by admin on 2017/10/04.
 */

public class RegisterResponse extends BaseResponse {
    private RegisterResponseData data;

    public RegisterResponseData getData() {
        return data;
    }

    public void setData(RegisterResponseData data) {
        this.data = data;
    }
}
