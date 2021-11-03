package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/10/08.
 */

public class ValidateRequest {
    @SerializedName("staff_no")
    private String staffNumber;

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }
}
