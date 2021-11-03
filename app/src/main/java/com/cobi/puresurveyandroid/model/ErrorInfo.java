package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorInfo {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("message")
    @Expose
    private List<ResponseMessage> message = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<ResponseMessage> getMessage() {
        return message;
    }

    public void setMessage(List<ResponseMessage> message) {
        this.message = message;
    }

}
