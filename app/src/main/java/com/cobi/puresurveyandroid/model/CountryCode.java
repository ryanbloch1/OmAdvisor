package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryCode {

    @SerializedName("domainName")
    @Expose
    private String domainName;
    @SerializedName("domainKey")
    @Expose
    private String domainKey;
    @SerializedName("domainDisplayValue")
    @Expose
    private String domainDisplayValue;
    @SerializedName("relatedDomainName")
    @Expose
    private String relatedDomainName;
    @SerializedName("relatedDomainKey")
    @Expose
    private String relatedDomainKey;
    @SerializedName("relatedDomainDisplayValue")
    @Expose
    private String relatedDomainDisplayValue;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainKey() {
        return domainKey;
    }

    public void setDomainKey(String domainKey) {
        this.domainKey = domainKey;
    }

    public String getDomainDisplayValue() {
        return domainDisplayValue;
    }

    public void setDomainDisplayValue(String domainDisplayValue) {
        this.domainDisplayValue = domainDisplayValue;
    }

    public String getRelatedDomainName() {
        return relatedDomainName;
    }

    public void setRelatedDomainName(String relatedDomainName) {
        this.relatedDomainName = relatedDomainName;
    }

    public String getRelatedDomainKey() {
        return relatedDomainKey;
    }

    public void setRelatedDomainKey(String relatedDomainKey) {
        this.relatedDomainKey = relatedDomainKey;
    }

    public String getRelatedDomainDisplayValue() {
        return relatedDomainDisplayValue;
    }

    public void setRelatedDomainDisplayValue(String relatedDomainDisplayValue) {
        this.relatedDomainDisplayValue = relatedDomainDisplayValue;
    }
}
