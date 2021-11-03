package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventMessage {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("eventId")
    @Expose
    public String eventId;
    @SerializedName("digitalId")
    @Expose
    public String digitalId;
    @SerializedName("postId")
    @Expose
    public String postId;
    @SerializedName("clientName")
    @Expose
    public String clientName;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("isAnonymous")
    @Expose
    public Boolean isAnonymous;
    @SerializedName("likes")
    @Expose
    public int likes;
    @SerializedName("dateLogged")
    @Expose
    private String dateLogged;
    @SerializedName("replies")
    @Expose
    private List<EventMessage> replies = null;
    @SerializedName("isLiked")
    @Expose
    private Boolean isLiked;
    @SerializedName("clientColor")
    @Expose
    public String clientColor;

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    public String getClientColor() {
        return clientColor;
    }

    public void setClientColor(String clientColor) {
        this.clientColor = clientColor;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean liked) {
        isLiked = liked;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDateLogged() {
        return dateLogged;
    }

    public void setDateLogged(String dateLogged) {
        this.dateLogged = dateLogged;
    }

    public List<EventMessage> getReplies() {
        return replies;
    }

    public void setReplies(List<EventMessage> replies) {
        this.replies = replies;
    }
}
