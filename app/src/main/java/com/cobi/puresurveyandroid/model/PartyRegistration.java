
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartyRegistration {

    @SerializedName("externalReference")
    @Expose
    private String externalReference;
    @SerializedName("typeName")
    @Expose
    private String typeName;
    @SerializedName("isRegisteredBySoftwareSystem")
    @Expose
    private String isRegisteredBySoftwareSystem;
    @SerializedName("issueDate")
    @Expose
    private String issueDate;
    @SerializedName("isRegisteredByCountry")
    @Expose
    private String isRegisteredByCountry;
    @SerializedName("nationality")
    @Expose
    private String nationality;

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIsRegisteredBySoftwareSystem() {
        return isRegisteredBySoftwareSystem;
    }

    public void setIsRegisteredBySoftwareSystem(String isRegisteredBySoftwareSystem) {
        this.isRegisteredBySoftwareSystem = isRegisteredBySoftwareSystem;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getIsRegisteredByCountry() {
        return isRegisteredByCountry;
    }

    public void setIsRegisteredByCountry(String isRegisteredByCountry) {
        this.isRegisteredByCountry = isRegisteredByCountry;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

}
