
        package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("notificationId")
    @Expose
    private String notificationId;
    @SerializedName("header")
    @Expose
    private String header;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("isOptedIn")
    @Expose
    private Boolean isOptedIn;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String title) {
        this.header = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getIsOptedIn() {
        return isOptedIn;
    }

    public void setIsOptedIn(Boolean isOptedIn) {
        this.isOptedIn = isOptedIn;
    }

}