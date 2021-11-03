package com.cobi.puresurveyandroid.model;

public class Invitation {

    String eventName;
    String location;
    String duration;
    String time;
    Boolean signedUp;
    int photoId;

    public Invitation(String eventName, String location, String duration, String time, Boolean signedUp, int photoId) {
        this.eventName = eventName;
        this.location = location;
        this.duration = duration;
        this.time = time;
        this.signedUp = signedUp;
        this.photoId = photoId;
    }
}
