package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewSecurityKey {

    public NewSecurityKey(String startDate, String endDate, String encryptionType, List<ConsistsOf> consistsOf) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.encryptionType = encryptionType;
        this.consistsOf = consistsOf;
    }

    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("encryptionType")
    @Expose
    private String encryptionType;
    @SerializedName("consistsOf")
    @Expose
    private List<ConsistsOf> consistsOf = null;

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

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public List<ConsistsOf> getConsistsOf() {
        return consistsOf;
    }

    public void setConsistsOf(List<ConsistsOf> consistsOf) {
        this.consistsOf = consistsOf;
    }

}
