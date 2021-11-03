package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TelephoneNumber {

    public TelephoneNumber(String countryPhoneCode, String localNumber, String electronicType) {
        this.countryPhoneCode = countryPhoneCode;
        this.localNumber = localNumber;
        this.electronicType = electronicType;
    }

    @SerializedName("countryPhoneCode")
    @Expose
    private String countryPhoneCode;
    @SerializedName("localNumber")
    @Expose
    private String localNumber;
    @SerializedName("electronicType")
    @Expose
    private String electronicType;

    public String getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public void setCountryPhoneCode(String countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }

    public String getLocalNumber() {
        return localNumber;
    }

    public void setLocalNumber(String localNumber) {
        this.localNumber = localNumber;
    }

    public String getElectronicType() {
        return electronicType;
    }

    public void setElectronicType(String electronicType) {
        this.electronicType = electronicType;
    }
}
