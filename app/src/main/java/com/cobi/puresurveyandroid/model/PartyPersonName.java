
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartyPersonName {

    @SerializedName("nameType")
    @Expose
    private String nameType;
    @SerializedName("initials")
    @Expose
    private String initials;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("middleNames")
    @Expose
    private String middleNames;
    @SerializedName("prefixTitles")
    @Expose
    private String prefixTitles;
    @SerializedName("suffixTitles")
    @Expose
    private String suffixTitles;
    @SerializedName("shortFirstName")
    @Expose
    private String shortFirstName;
    @SerializedName("fullName")
    @Expose
    private String fullName;

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
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

    public String getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(String middleNames) {
        this.middleNames = middleNames;
    }

    public String getPrefixTitles() {
        return prefixTitles;
    }

    public void setPrefixTitles(String prefixTitles) {
        this.prefixTitles = prefixTitles;
    }

    public String getSuffixTitles() {
        return suffixTitles;
    }

    public void setSuffixTitles(String suffixTitles) {
        this.suffixTitles = suffixTitles;
    }

    public String getShortFirstName() {
        return shortFirstName;
    }

    public void setShortFirstName(String shortFirstName) {
        this.shortFirstName = shortFirstName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
