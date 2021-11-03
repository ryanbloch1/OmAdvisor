
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineStreetAddress {

    @SerializedName("addressLines")
    @Expose
    private String addressLines;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("residential")
    @Expose
    private String residential;

    public String getAddressLines() {
        return addressLines;
    }

    public void setAddressLines(String addressLines) {
        this.addressLines = addressLines;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getResidential() {
        return residential;
    }

    public void setResidential(String residential) {
        this.residential = residential;
    }

}
