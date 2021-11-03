package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResetPasswordRequest {

    @SerializedName("newSecurityKey")
    @Expose
    private NewSecurityKey newSecurityKey;

    public NewSecurityKey getNewSecurityKey() {
        return newSecurityKey;
    }

    public void setNewSecurityKey(NewSecurityKey newSecurityKey) {
        this.newSecurityKey = newSecurityKey;
    }


    public ResetPasswordRequest(String  startDate, String endDate, String contactNumber, String userId, String phoneCode) {

        List<ConsistsOf> consistsOfs = new ArrayList<>();

        consistsOfs.add(new ConsistsOf("TelephoneNumber.localNumber", contactNumber));
        consistsOfs.add(new ConsistsOf("userId", userId));
        consistsOfs.add(new ConsistsOf("TelephoneNumber.countryPhoneCode", phoneCode));
        consistsOfs.add(new ConsistsOf("TelephoneNumber.electronicType", "Mobile"));
        consistsOfs.add(new ConsistsOf("ForceChangePassword", "false"));

        this.newSecurityKey = new NewSecurityKey(startDate, endDate, "", consistsOfs);
    }

}
