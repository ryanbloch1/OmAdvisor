package com.cobi.puresurveyandroid.model;

public class PostUserRequest {

    public String DigitalId;
    public String SalesCode;
    public String FirstName;
    public String LastName;
    public String EmailAddress;
    public String Color;

    public PostUserRequest(String digitalId, String salesCode, String firstName, String lastName, String emailAddress, String color) {
        DigitalId = digitalId;
        SalesCode =salesCode;
        FirstName = firstName;
        LastName = lastName;
        EmailAddress = emailAddress;
        Color = color;
    }

    public String getSalesCode() {
        return SalesCode;
    }

    public void setSalesCode(String salesCode) {
        SalesCode = salesCode;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getDigitalId() {
        return DigitalId;
    }

    public void setDigitalId(String digitalId) {
        DigitalId = digitalId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }
}
