package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/10/05.
 */

public class CommissionResponse extends BaseResponse {

    @SerializedName("data")
    private CommissionData data;

    public CommissionData getData() {
        return data;
    }

    public void setData(CommissionData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommissionResponse{" + "data=" + data + '}';
    }
}
