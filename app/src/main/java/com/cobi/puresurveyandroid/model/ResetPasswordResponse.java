package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordResponse {

    @SerializedName("newSecurityKey")
    @Expose

    private NewSecurityKey newSecurityKey;
    @SerializedName("errorInfo")
    @Expose
    private ErrorInfo errorInfo;

    public NewSecurityKey getNewSecurityKey() {
        return newSecurityKey;
    }

    public void setNewSecurityKey(NewSecurityKey newSecurityKey) {
        this.newSecurityKey = newSecurityKey;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }
}
