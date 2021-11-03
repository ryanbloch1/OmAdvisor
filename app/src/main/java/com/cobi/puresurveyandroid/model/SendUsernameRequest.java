package com.cobi.puresurveyandroid.model;

public class SendUsernameRequest {


    public SendUsernameRequest(String personName, String code, String localNo, String electronicType) {
        this.personName = new PersonNameModel(personName);
        telephoneNumber = new TelephoneNumber(code, localNo, electronicType);
    }

    private PersonNameModel personName;
    private TelephoneNumber telephoneNumber;

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

}