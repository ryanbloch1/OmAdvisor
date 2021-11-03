
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Holdings {

    @SerializedName("funeralCover")
    @Expose
    private String funeralCover;
    @SerializedName("disabilityCover")
    @Expose
    private String disabilityCover;
    @SerializedName("lifeCover")
    @Expose
    private String lifeCover;
    @SerializedName("omCustomerSince")
    @Expose
    private String omCustomerSince;
    @SerializedName("illnessCover")
    @Expose
    private String illnessCover;
    @SerializedName("savingsAndInvestment")
    @Expose
    private String savingsAndInvestment;

    public String getFuneralCover() {
        return funeralCover;
    }

    public void setFuneralCover(String funeralCover) {
        this.funeralCover = funeralCover;
    }

    public String getDisabilityCover() {
        return disabilityCover;
    }

    public void setDisabilityCover(String disabilityCover) {
        this.disabilityCover = disabilityCover;
    }

    public String getLifeCover() {
        return lifeCover;
    }

    public void setLifeCover(String lifeCover) {
        this.lifeCover = lifeCover;
    }

    public String getOmCustomerSince() {
        return omCustomerSince;
    }

    public void setOmCustomerSince(String omCustomerSince) {
        this.omCustomerSince = omCustomerSince;
    }

    public String getIllnessCover() {
        return illnessCover;
    }

    public void setIllnessCover(String illnessCover) {
        this.illnessCover = illnessCover;
    }

    public String getSavingsAndInvestment() {
        return savingsAndInvestment;
    }

    public void setSavingsAndInvestment(String savingsAndInvestment) {
        this.savingsAndInvestment = savingsAndInvestment;
    }

}