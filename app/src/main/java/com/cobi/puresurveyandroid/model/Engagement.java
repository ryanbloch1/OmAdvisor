
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Engagement {

    @SerializedName("detailsLastUpdated")
    @Expose
    private String detailsLastUpdated;
    @SerializedName("lastBought")
    @Expose
    private String lastBought;
    @SerializedName("optOut")
    @Expose
    private Boolean optOut;
    @SerializedName("rewardTierNew")
    @Expose
    private String rewardTierNew;
    @SerializedName("rwClientInd")
    @Expose
    private Boolean rwClientInd;

    public String getRewardTierNew() {
        return rewardTierNew;
    }

    public Boolean getRwClientInd() {
        return rwClientInd;
    }

    public String getDetailsLastUpdated() {
        return detailsLastUpdated;
    }

    public void setDetailsLastUpdated(String detailsLastUpdated) {
        this.detailsLastUpdated = detailsLastUpdated;
    }

    public String getLastBought() {
        return lastBought;
    }

    public void setLastBought(String lastBought) {
        this.lastBought = lastBought;
    }

    public Boolean getOptOut() {
        return optOut;
    }

    public void setOptOut(Boolean optOut) {
        this.optOut = optOut;
    }

}