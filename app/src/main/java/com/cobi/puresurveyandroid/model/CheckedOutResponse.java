package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckedOutResponse {
    @SerializedName("isSuccessRequest")
    @Expose
    private Boolean isCheckedOut;

    public Boolean getIsCheckedOut() {
        return isCheckedOut;
    }

    public void setIsCheckedOut(Boolean isSuccessRequest) {
        this.isCheckedOut = isSuccessRequest;
    }
}
