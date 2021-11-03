
package com.cobi.puresurveyandroid.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName("externalReference")
    @Expose
    private String externalReference;
    @SerializedName("birthDate")
    @Expose
    private String birthDate;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("PersonName")
    @Expose
    private PersonName personName;
    @SerializedName("MobileNumber")
    @Expose
    private List<MobileNumber> mobileNumber = null;
    @SerializedName("EmailAddress")
    @Expose
    private List<EmailAddress> emailAddress = null;
    @SerializedName("LineStreetAddress")
    @Expose
    private List<LineStreetAddress> lineStreetAddress = null;
    @SerializedName("PartyRegistration")
    @Expose
    private List<PartyRegistration> partyRegistration = null;
    @SerializedName("ChannelRole")
    @Expose
    private List<ChannelRole> channelRole = null;

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

    public PersonName getPersonName() {
        return personName;
    }

    public void setPersonName(PersonName personName) {
        this.personName = personName;
    }

    public List<MobileNumber> getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(List<MobileNumber> mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public List<EmailAddress> getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(List<EmailAddress> emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<LineStreetAddress> getLineStreetAddress() {
        return lineStreetAddress;
    }

    public void setLineStreetAddress(List<LineStreetAddress> lineStreetAddress) {
        this.lineStreetAddress = lineStreetAddress;
    }

    public List<PartyRegistration> getPartyRegistration() {
        return partyRegistration;
    }

    public void setPartyRegistration(List<PartyRegistration> partyRegistration) {
        this.partyRegistration = partyRegistration;
    }

    public List<ChannelRole> getChannelRole() {
        return channelRole;
    }

    public void setChannelRole(List<ChannelRole> channelRole) {
        this.channelRole = channelRole;
    }

}
