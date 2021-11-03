
package com.cobi.puresurveyandroid.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonObjectParty {

    @SerializedName("externalReference")
    @Expose
    private String externalReference;
    @SerializedName("birthDate")
    @Expose
    private String birthDate;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;
    @SerializedName("maritalContractType")
    @Expose
    private String maritalContractType;
    @SerializedName("PersonName")
    @Expose
    private List<PartyPersonName> personName = null;
    @SerializedName("ContactPreference")
    @Expose
    private List<ContactPreference> contactPreference = null;
    @SerializedName("PartyRoles")
    @Expose
    private List<Object> partyRoles = null;
    @SerializedName("PartyRegistration")
    @Expose
    private List<PartyRegistration> partyRegistration = null;

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMaritalContractType() {
        return maritalContractType;
    }

    public void setMaritalContractType(String maritalContractType) {
        this.maritalContractType = maritalContractType;
    }

    public List<PartyPersonName> getPersonName() {
        return personName;
    }

    public void setPersonName(List<PartyPersonName> personName) {
        this.personName = personName;
    }

    public List<ContactPreference> getContactPreference() {
        return contactPreference;
    }

    public void setContactPreference(List<ContactPreference> contactPreference) {
        this.contactPreference = contactPreference;
    }

    public List<Object> getPartyRoles() {
        return partyRoles;
    }

    public void setPartyRoles(List<Object> partyRoles) {
        this.partyRoles = partyRoles;
    }

    public List<PartyRegistration> getPartyRegistration() {
        return partyRegistration;
    }

    public void setPartyRegistration(List<PartyRegistration> partyRegistration) {
        this.partyRegistration = partyRegistration;
    }

}
