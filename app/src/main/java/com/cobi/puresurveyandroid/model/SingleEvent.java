
package com.cobi.puresurveyandroid.model;

import com.cobi.puresurveyandroid.util.DateHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleEvent {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("segmentId")
    @Expose
    private String segmentId;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("coordinates")
    @Expose
    private Object coordinates;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("removalDate")
    @Expose
    private String removalDate;
    @SerializedName("eventDuration")
    @Expose
    private Integer eventDuration;
    @SerializedName("discussionTopics")
    @Expose
    private String discussionTopics;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("cancelMessage")
    @Expose
    private Object cancelMessage;
    @SerializedName("isCheckedIn")
    @Expose
    private Boolean isCheckedIn;
    @SerializedName("hasCheckedOut")
    @Expose
    private Boolean hasCheckedOut;

    @SerializedName("agenda")
    @Expose
    private String agenda;
    public int getRegistrationStatus() {
        return RegistrationStatus;
    }

    @SerializedName("registrationStatus")
    @Expose
    private int RegistrationStatus;

    public boolean isUpcoming() {
        return DateHelper.isEventUpcoming(startDate);
    }

    public boolean isStillActive() {
        return DateHelper.isEventStillActive(startDate,removalDate);
    }


    public boolean isEndedButActive() {
        return DateHelper.isEventStillActive(endDate,removalDate);
    }

    public Boolean getHasCheckedOut() {
        return hasCheckedOut;
    }

    public void setHasCheckedOut(Boolean hasCheckedOut) {
        this.hasCheckedOut = hasCheckedOut;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public boolean isLive() {
        return DateHelper.isEventLive(startDate, endDate);
    }


    public boolean isPassed() {
        return DateHelper.isEventPassed(endDate);
    }

    public String getRemovalDate() {
        return removalDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Object getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Object coordinates) {
        this.coordinates = coordinates;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(Integer eventDuration) {
        this.eventDuration = eventDuration;
    }

    public String getDiscussionTopics() {
        return discussionTopics;
    }

    public void setDiscussionTopics(String discussionTopics) {
        this.discussionTopics = discussionTopics;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getCancelMessage() {
        return cancelMessage;
    }

    public void setCancelMessage(Object cancelMessage) {
        this.cancelMessage = cancelMessage;
    }

    public Boolean getCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(Boolean checkedIn) {
        isCheckedIn = checkedIn;
    }



}
