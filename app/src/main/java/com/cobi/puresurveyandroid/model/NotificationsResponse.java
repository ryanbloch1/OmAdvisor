package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationsResponse {

    @SerializedName("nonchangeable")
    @Expose
    private List<Notification> nonchangeable = null;
    @SerializedName("changeable")
    @Expose
    private List<Notification> changeable = null;

    public List<Notification> getNonchangeable() {
        return nonchangeable;
    }

    public void setNonchangeable(List<Notification> nonchangeable) {
        this.nonchangeable = nonchangeable;
    }

    public List<Notification> getChangeable() {
        return changeable;
    }

    public void setChangeable(List<Notification> changeable) {
        this.changeable = changeable;
    }

}