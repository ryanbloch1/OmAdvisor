
package com.cobi.puresurveyandroid.model;

import java.util.List;

import com.cobi.puresurveyandroid.model.AdminRequirement;
import com.cobi.puresurveyandroid.model.UwRequirement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutstandingReqParty {

    @SerializedName("bancsBpKey")
    @Expose
    private String bancsBpKey;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("uwRequirements")
    @Expose
    private List<UwRequirement> uwRequirements = null;
    @SerializedName("adminRequirements")
    @Expose
    private List<AdminRequirement> adminRequirements = null;

    public String getBancsBpKey() {
        return bancsBpKey;
    }

    public void setBancsBpKey(String bancsBpKey) {
        this.bancsBpKey = bancsBpKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<UwRequirement> getUwRequirements() {
        return uwRequirements;
    }

    public void setUwRequirements(List<UwRequirement> uwRequirements) {
        this.uwRequirements = uwRequirements;
    }

    public List<AdminRequirement> getAdminRequirements() {
        return adminRequirements;
    }

    public void setAdminRequirements(List<AdminRequirement> adminRequirements) {
        this.adminRequirements = adminRequirements;
    }

}
