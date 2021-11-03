package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckedInResponse {
    @SerializedName("isSuccessRequest")
    @Expose
    private Boolean isCheckedIn;

    public Boolean getIsCheckedIn() {
        return isCheckedIn;
    }

    public void setIsSuccessRequest(Boolean isSuccessRequest) {
        this.isCheckedIn = isSuccessRequest;
    }
}
