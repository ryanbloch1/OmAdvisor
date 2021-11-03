
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactPoint {

    @SerializedName("PhoneType")
    @Expose
    private String phoneType;
    @SerializedName("CountryCode")
    @Expose
    private String countryCode;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("AreaCode")
    @Expose
    private String areaCode;
    @SerializedName("CountryPhoneCode")
    @Expose
    private String countryPhoneCode;
    @SerializedName("IsFax")
    @Expose
    private String isFax;
    @SerializedName("IsPreferred")
    @Expose
    private String isPreferred;
    @SerializedName("Usage")
    @Expose
    private String usage;

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public void setCountryPhoneCode(String countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }

    public String getIsFax() {
        return isFax;
    }

    public void setIsFax(String isFax) {
        this.isFax = isFax;
    }

    public String getIsPreferred() {
        return isPreferred;
    }

    public void setIsPreferred(String isPreferred) {
        this.isPreferred = isPreferred;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

}
