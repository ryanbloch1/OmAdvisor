package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImedTokenResponse {

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}