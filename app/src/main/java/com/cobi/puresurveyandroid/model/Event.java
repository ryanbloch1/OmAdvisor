
package com.cobi.puresurveyandroid.model;

import java.util.List;

import com.cobi.puresurveyandroid.util.DateHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event  {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("dateFrom")
    @Expose
    private String dateFrom;
    @SerializedName("dateTo")
    @Expose
    private String dateTo;
    @SerializedName("removalDate")
    @Expose
    private String removalDate;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("isCheckedIn")
    @Expose
    private Boolean isCheckedIn;
    @SerializedName("hasCheckedOut")
    @Expose
    private Boolean hasCheckedOut;
    @SerializedName("media")
    @Expose
    private List<Medium> media = null;

    public int getRegistrationStatus() {
        return RegistrationStatus;
    }

    @SerializedName("registrationStatus")
    @Expose
    private int RegistrationStatus;

    public boolean isLive() {
        return DateHelper.isEventLive(dateFrom, dateTo);
    }


    public boolean isPassed() {
        return DateHelper.isEventPassed(dateTo);
    }

    public boolean isUpcoming() {
        return DateHelper.isEventUpcoming(dateFrom);
    }


    public String getFormattedDateTime(){
        return DateHelper.getDayOfWeekDate(dateFrom);
    }


    public Boolean getHasCheckedOut() {
        return hasCheckedOut;
    }

    public void setHasCheckedOut(Boolean hasCheckedOut) {
        this.hasCheckedOut = hasCheckedOut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    public Boolean getCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(Boolean checkedIn) {
        isCheckedIn = checkedIn;
    }


}
