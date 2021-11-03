package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendUsernameResponse {

    @SerializedName("personName")
    @Expose
    private PersonNameModel personName;
    @SerializedName("telephoneNumber")
    @Expose
    private TelephoneNumber telephoneNumber;
    @SerializedName("errorInfo")
    @Expose
    private ErrorInfo errorInfo;

    public PersonNameModel getPersonName() {
        return personName;
    }

    public void setPersonName(PersonNameModel personName) {
        this.personName = personName;
    }

    public TelephoneNumber getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(TelephoneNumber telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

}
