package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCustomerCounts {

    @SerializedName("BirthdaysThisMonth")
    @Expose
    private Integer birthdaysThisMonth;
    @SerializedName("ActiveLeads")
    @Expose
    private Integer activeLeads;
    @SerializedName("ContractingParties")
    @Expose
    private Integer contractingParties;
    @SerializedName("MissedPremiums")
    @Expose
    private Integer missedPremiums;

    public Integer getBirthdaysThisMonth() {
        return birthdaysThisMonth;
    }

    public void setBirthdaysThisMonth(Integer birthdaysThisMonth) {
        this.birthdaysThisMonth = birthdaysThisMonth;
    }

    public Integer getActiveLeads() {
        return activeLeads;
    }

    public void setActiveLeads(Integer activeLeads) {
        this.activeLeads = activeLeads;
    }

    public Integer getContractingParties() {
        return contractingParties;
    }

    public void setContractingParties(Integer contractingParties) {
        this.contractingParties = contractingParties;
    }

    public Integer getMissedPremiums() {
        return missedPremiums;
    }

    public void setMissedPremiums(Integer missedPremiums) {
        this.missedPremiums = missedPremiums;
    }

}
