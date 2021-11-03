package com.cobi.puresurveyandroid.model;

public class PostMessageRequest {
    public String eventId;
    public String digitalId;
    public String postId;
    public String clientName;
    public String message;
    public String image;
    public Boolean isAnonymous;

    public PostMessageRequest(String eventId, String digitalId, String postId, String clientName, String message, String image, Boolean isAnonymous) {
        this.eventId = eventId;
        this.digitalId = digitalId;
        this.postId = postId;
        this.clientName = clientName;
        this.message = message;
        this.image = image;
        this.isAnonymous = isAnonymous;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(String digitalId) {
        this.digitalId = digitalId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }
}
