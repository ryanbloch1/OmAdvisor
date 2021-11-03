package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/10/04.
 */

public class SaveTargetRequest {

    @SerializedName("target")
    private int target;

    public SaveTargetRequest(int target) {
        this.target = target;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
