package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageSection {

    @SerializedName("sectionKey")
    @Expose
    private String sectionKey;
    @SerializedName("sectionMessage")
    @Expose
    private String sectionMessage;

    public String getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(String sectionKey) {
        this.sectionKey = sectionKey;
    }

    public String getSectionMessage() {
        return sectionMessage;
    }

    public void setSectionMessage(String sectionMessage) {
        this.sectionMessage = sectionMessage;
    }
}
