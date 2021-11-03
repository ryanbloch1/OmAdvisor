
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Affordability {

    @SerializedName("investmentBand")
    @Expose
    private String investmentBand;
    @SerializedName("glLapse")
    @Expose
    private String glLapse;
    @SerializedName("mxLapse")
    @Expose
    private String mxLapse;
    @SerializedName("incomeBand")
    @Expose
    private String incomeBand;
    @SerializedName("hasBond")
    @Expose
    private Boolean hasBond;
    @SerializedName("ownsProperty")
    @Expose
    private Boolean ownsProperty;

    public String getInvestmentBand() {
        return investmentBand;
    }

    public void setInvestmentBand(String investmentBand) {
        this.investmentBand = investmentBand;
    }

    public String getGlLapse() {
        return glLapse;
    }

    public void setGlLapse(String glLapse) {
        this.glLapse = glLapse;
    }

    public String getMxLapse() {
        return mxLapse;
    }

    public void setMxLapse(String mxLapse) {
        this.mxLapse = mxLapse;
    }

    public String getIncomeBand() {
        return incomeBand;
    }

    public void setIncomeBand(String incomeBand) {
        this.incomeBand = incomeBand;
    }

    public Boolean getHasBond() {
        return hasBond;
    }

    public void setHasBond(Boolean hasBond) {
        this.hasBond = hasBond;
    }

    public Boolean getOwnsProperty() {
        return ownsProperty;
    }

    public void setOwnsProperty(Boolean ownsProperty) {
        this.ownsProperty = ownsProperty;
    }

}