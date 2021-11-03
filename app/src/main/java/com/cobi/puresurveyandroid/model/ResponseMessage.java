package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMessage {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("messageSections")
    @Expose
    private List<MessageSection> messageSections = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MessageSection> getMessageSections() {
        return messageSections;
    }

    public void setMessageSections(List<MessageSection> messageSections) {
        this.messageSections = messageSections;
    }
}
