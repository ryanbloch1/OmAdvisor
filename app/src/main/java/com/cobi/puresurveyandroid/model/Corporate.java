
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Corporate {

    @SerializedName("corporateScheme")
    @Expose
    private String corporateScheme;
    @SerializedName("corporateClient")
    @Expose
    private Boolean corporateClient;

    public String getCorporateScheme() {
        return corporateScheme;
    }

    public void setCorporateScheme(String corporateScheme) {
        this.corporateScheme = corporateScheme;
    }

    public Boolean getCorporateClient() {
        return corporateClient;
    }

    public void setCorporateClient(Boolean corporateClient) {
        this.corporateClient = corporateClient;
    }

}