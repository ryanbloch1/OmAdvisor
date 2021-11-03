package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Advisor {



    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("SalesCode")
    @Expose
    private String salesCode;
    @SerializedName("Surname")
    @Expose
    private String surname;
    @SerializedName("FirstNames")
    @Expose
    private String firstnames;
    @SerializedName("BusinessUnit")
    @Expose
    private String businessUnit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSalesCode() {
        return salesCode;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstnames() {
        return firstnames;
    }

    public void setFirstnames(String firstnames) {
        this.firstnames = firstnames;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }



}