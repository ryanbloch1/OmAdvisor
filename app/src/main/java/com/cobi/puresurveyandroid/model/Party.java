
package com.cobi.puresurveyandroid.model;

import java.util.Comparator;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Party{

    @SerializedName("BancsBpKey")
    @Expose
    private String bancsBpKey;
    @SerializedName("PartyType")
    @Expose
    private String partyType;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("LegalStructure")
    @Expose
    private String legalStructure;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("RegistrationNumber")
    @Expose
    private String registrationNumber;
    @SerializedName("Roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("ContactPoints")
    @Expose
    private List<ContactPoint> contactPoints = null;
    @SerializedName("ElectronicAddresses")
    @Expose
    private List<ElectronicAddress> electronicAddresses = null;

    String selectedRole;


    String dateTimeStampForApplicationInstance;


    public String getDateTimeStampForApplicationInstance() {
        return dateTimeStampForApplicationInstance;
    }

    public void setDateTimeStampForApplicationInstance(String dateTimeStampForApplicationInstance) {
        this.dateTimeStampForApplicationInstance = dateTimeStampForApplicationInstance;
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }

    private String applicationNumber;

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getBancsBpKey() {
        return bancsBpKey;
    }

    public void setBancsBpKey(String bancsBpKey) {
        this.bancsBpKey = bancsBpKey;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
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

    public String getLegalStructure() {
        return legalStructure;
    }

    public void setLegalStructure(String legalStructure) {
        this.legalStructure = legalStructure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<ContactPoint> getContactPoints() {
        return contactPoints;
    }

    public void setContactPoints(List<ContactPoint> contactPoints) {
        this.contactPoints = contactPoints;
    }

    public List<ElectronicAddress> getElectronicAddresses() {
        return electronicAddresses;
    }

    public void setElectronicAddresses(List<ElectronicAddress> electronicAddresses) {
        this.electronicAddresses = electronicAddresses;
    }


    public static Comparator<Party> pComparator = new Comparator<Party>() {

        public int compare(Party s1, Party s2) {

            if(s1.getLastName()!=null && s2.getLastName()!=null){
                return s1.getLastName().toLowerCase().compareTo(s2.getLastName().toLowerCase());

            }


            return 0;
        }};

}
