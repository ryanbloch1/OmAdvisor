
package com.cobi.puresurveyandroid.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IMEDDetails extends BaseResponse {

    @SerializedName("IntermediaryDetails")
    @Expose
    private List<IntermediaryDetail> intermediaryDetails;

    public List<IntermediaryDetail> getIntermediaryDetails() {
        return intermediaryDetails;
    }

    public void setIntermediaryDetails(List<IntermediaryDetail> intermediaryDetails) {
        this.intermediaryDetails = intermediaryDetails;
    }

}
