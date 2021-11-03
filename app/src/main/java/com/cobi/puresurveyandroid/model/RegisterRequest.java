package com.cobi.puresurveyandroid.model;

import com.cobi.puresurveyandroid.BuildConfig;
import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/10/04.
 */

public class RegisterRequest {

    @SerializedName("staff_no")
    private String staffNumber;
    private String imei;
    private String imsi;
    private String manufacturer;
    @SerializedName("phone_model")
    private String phoneModel;
    @SerializedName("push_token")
    private String pushToken;
    @SerializedName("client_version")
    private String clientVersion;

    public RegisterRequest() {

        phoneModel = PureSurveyApplication.MODEL;
        imei = PureSurveyApplication.IMEI;
        imsi = PureSurveyApplication.IMSI;
        manufacturer = PureSurveyApplication.MANUFACTURER;
        clientVersion = BuildConfig.VERSION_NAME;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }
}
