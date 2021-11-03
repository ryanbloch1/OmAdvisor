
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OmpClientsResponse {

    @SerializedName("AdviserSubmit")
    @Expose
    private AdviserSubmit adviserSubmit;

    public AdviserSubmit getAdviserSubmit() {
        return adviserSubmit;
    }

    public void setAdviserSubmit(AdviserSubmit adviserSubmit) {
        this.adviserSubmit = adviserSubmit;
    }

}
