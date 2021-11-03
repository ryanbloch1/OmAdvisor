
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntermediaryAgreementSpecification {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("territory")
    @Expose
    private String territory;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

}
