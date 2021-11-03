
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cpd {

    public Cpd(Integer requiredHours, Integer attainedHours) {
        this.requiredHours = requiredHours;
        this.attainedHours = attainedHours;
    }

    @SerializedName("requiredHours")
    @Expose
    private Integer requiredHours;
    @SerializedName("attainedHours")
    @Expose
    private Integer attainedHours;
    @SerializedName("summary")
    @Expose
    private String summary;

    public Integer getRequiredHours() {
        return requiredHours;
    }

    public void setRequiredHours(Integer requiredHours) {
        this.requiredHours = requiredHours;
    }

    public Integer getAttainedHours() {
        return attainedHours;
    }

    public void setAttainedHours(Integer attainedHours) {
        this.attainedHours = attainedHours;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
