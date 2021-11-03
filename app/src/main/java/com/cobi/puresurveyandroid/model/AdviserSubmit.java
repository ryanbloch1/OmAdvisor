
package com.cobi.puresurveyandroid.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdviserSubmit {

    @SerializedName("ApplicationInstance")
    @Expose
    private String applicationInstance;
    @SerializedName("GroupingKey")
    @Expose
    private String groupingKey;
    @SerializedName("Product")
    @Expose
    private String product;
    @SerializedName("AdviserBpKey")
    @Expose
    private String adviserBpKey;
    @SerializedName("SalesCode")
    @Expose
    private String salesCode;
    @SerializedName("Segment")
    @Expose
    private String segment;
    @SerializedName("Channel")
    @Expose
    private String channel;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("DateTimestamp")
    @Expose
    private String dateTimestamp;
    @SerializedName("ReviewerExternalReference")
    @Expose
    private String reviewerExternalReference;
    @SerializedName("ReviewerFirstName")
    @Expose
    private String reviewerFirstName;
    @SerializedName("ReviewerLastName")
    @Expose
    private String reviewerLastName;
    @SerializedName("EscalatorExternalReference")
    @Expose
    private String escalatorExternalReference;
    @SerializedName("EscalatorFirstName")
    @Expose
    private String escalatorFirstName;
    @SerializedName("EscalatorLastName")
    @Expose
    private String escalatorLastName;
    @SerializedName("event_SourceEventType")
    @Expose
    private String event_SourceEventType;
    @SerializedName("TimeStamp")
    @Expose
    private String timeStamp;
    @SerializedName("Party")
    @Expose
    private List<Party> party = null;

    public String getApplicationInstance() {
        return applicationInstance;
    }

    public void setApplicationInstance(String applicationInstance) {
        this.applicationInstance = applicationInstance;
    }

    public String getGroupingKey() {
        return groupingKey;
    }

    public void setGroupingKey(String groupingKey) {
        this.groupingKey = groupingKey;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAdviserBpKey() {
        return adviserBpKey;
    }

    public void setAdviserBpKey(String adviserBpKey) {
        this.adviserBpKey = adviserBpKey;
    }

    public String getSalesCode() {
        return salesCode;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateTimestamp() {
        return dateTimestamp;
    }

    public void setDateTimestamp(String dateTimestamp) {
        this.dateTimestamp = dateTimestamp;
    }

    public String getReviewerExternalReference() {
        return reviewerExternalReference;
    }

    public void setReviewerExternalReference(String reviewerExternalReference) {
        this.reviewerExternalReference = reviewerExternalReference;
    }

    public String getReviewerFirstName() {
        return reviewerFirstName;
    }

    public void setReviewerFirstName(String reviewerFirstName) {
        this.reviewerFirstName = reviewerFirstName;
    }

    public String getReviewerLastName() {
        return reviewerLastName;
    }

    public void setReviewerLastName(String reviewerLastName) {
        this.reviewerLastName = reviewerLastName;
    }

    public String getEscalatorExternalReference() {
        return escalatorExternalReference;
    }

    public void setEscalatorExternalReference(String escalatorExternalReference) {
        this.escalatorExternalReference = escalatorExternalReference;
    }

    public String getEscalatorFirstName() {
        return escalatorFirstName;
    }

    public void setEscalatorFirstName(String escalatorFirstName) {
        this.escalatorFirstName = escalatorFirstName;
    }

    public String getEscalatorLastName() {
        return escalatorLastName;
    }

    public void setEscalatorLastName(String escalatorLastName) {
        this.escalatorLastName = escalatorLastName;
    }

    public String getEvent_SourceEventType() {
        return event_SourceEventType;
    }

    public void setEvent_SourceEventType(String event_SourceEventType) {
        this.event_SourceEventType = event_SourceEventType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<Party> getParty() {
        return party;
    }

    public void setParty(List<Party> party) {
        this.party = party;
    }

}
