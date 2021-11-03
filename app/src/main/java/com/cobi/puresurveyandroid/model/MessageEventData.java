package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageEventData {

    public List<UserCustomerEvent> events;
    @SerializedName("timestamp")
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<UserCustomerEvent> getEvents() {
        return events;
    }

    public void setEvents(List<UserCustomerEvent> events) {
        this.events = events;
    }
}
