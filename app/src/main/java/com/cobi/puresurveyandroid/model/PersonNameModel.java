package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonNameModel {

    public PersonNameModel(String lastName) {
        this.lastName = lastName;
    }

    @SerializedName("lastName")
    @Expose
    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
