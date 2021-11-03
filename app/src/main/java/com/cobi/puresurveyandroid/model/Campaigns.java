
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Campaigns {

    @SerializedName("lastArDate")
    @Expose
    private String lastArDate;

    public String getLastArDate() {
        return lastArDate;
    }

    public void setLastArDate(String lastArDate) {
        this.lastArDate = lastArDate;
    }

}