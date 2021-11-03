
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartySearchCriteria {

    @SerializedName("softSysRegSystem")
    @Expose
    private String softSysRegSystem;
    @SerializedName("softSysRegID")
    @Expose
    private String softSysRegID;

    public PartySearchCriteria(String softSysRegSystem, String softSysRegID) {
        this.softSysRegSystem = softSysRegSystem;
        this.softSysRegID = softSysRegID;
    }

    public String getSoftSysRegSystem() {
        return softSysRegSystem;
    }

    public void setSoftSysRegSystem(String softSysRegSystem) {
        this.softSysRegSystem = softSysRegSystem;
    }

    public String getSoftSysRegID() {
        return softSysRegID;
    }

    public void setSoftSysRegID(String softSysRegID) {
        this.softSysRegID = softSysRegID;
    }

}
