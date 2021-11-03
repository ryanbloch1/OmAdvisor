
package com.cobi.puresurveyandroid.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OmpOutstandingRequirements {

    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("instanceNumber")
    @Expose
    private String instanceNumber;
    @SerializedName("parties")
    @Expose
    private List<OutstandingReqParty> parties = null;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(String instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public List<OutstandingReqParty> getParties() {
        return parties;
    }

    public void setParties(List<OutstandingReqParty> parties) {
        this.parties = parties;
    }

}
