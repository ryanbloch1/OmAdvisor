package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2017/10/05.
 */

public class CommissionData implements Serializable {

    @SerializedName("target")
    private Integer target;
    @SerializedName("current")
    private Double current;

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
            this.target = target;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "CommissionData{" + "target=" + target + ", current=" + current + '}';
    }
}
