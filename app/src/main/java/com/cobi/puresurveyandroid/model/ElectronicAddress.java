
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ElectronicAddress {

    @SerializedName("emailType")
    @Expose
    private String emailType;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("isPreferred")
    @Expose
    private String isPreferred;

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getIsPreferred() {
        return isPreferred;
    }

    public void setIsPreferred(String isPreferred) {
        this.isPreferred = isPreferred;
    }

}
