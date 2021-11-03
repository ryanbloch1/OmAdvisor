package com.cobi.puresurveyandroid.model;

public class DemographicResponse {

    String location;
    String blurb;
    String name;


    public DemographicResponse(String location, String blurb, String name) {
        this.location = location;
        this.blurb = blurb;
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public String getBlurb() {
        return blurb;
    }

    public String getName() {
        return name;
    }
}
