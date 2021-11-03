package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OnboardingInfo implements Serializable {

    @SerializedName("image_url")
    public String imageUrl;
    @SerializedName("heading")
    public String heading;
    @SerializedName("body")
    public String body;

    public OnboardingInfo(String imageUrl, String heading, String body) {
        this.imageUrl = imageUrl;
        this.heading = heading;
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
