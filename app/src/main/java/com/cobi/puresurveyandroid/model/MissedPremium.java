
package com.cobi.puresurveyandroid.model;

import com.cobi.puresurveyandroid.util.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MissedPremium {

    @SerializedName("contractNumber")
    @Expose
    private String contractNumber;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("lapse_CoolOffDate")
    @Expose
    private String lapseCoolOffDate;
    @SerializedName("contractingParty")
    @Expose
    private String contractingParty;
    @SerializedName("clawback")
    @Expose
    private String clawback;
    @SerializedName("cellNumber")
    @Expose
    private String cellNumber;
    @SerializedName("homeTelephone")
    @Expose
    private String homeTelephone;
    @SerializedName("workTelephone")
    @Expose
    private String workTelephone;
    @SerializedName("missedPremiumValue")
    @Expose
    private String missedPremiumValue;
    @SerializedName("email")
    @Expose
    private String email;

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getLapseCoolOffDate() {
        return lapseCoolOffDate;
    }

    public void setLapseCoolOffDate(String lapseCoolOffDate) {
        this.lapseCoolOffDate = lapseCoolOffDate;
    }

    public String getContractingParty() {
        return contractingParty;
    }

    public void setContractingParty(String contractingParty) {
        this.contractingParty = contractingParty;
    }

    public String getClawback() {
        return clawback;
    }

    public void setClawback(String clawback) {
        this.clawback = clawback;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getHomeTelephone() {
        return homeTelephone;
    }

    public void setHomeTelephone(String homeTelephone) {
        this.homeTelephone = homeTelephone;
    }

    public String getWorkTelephone() {
        return workTelephone;
    }

    public void setWorkTelephone(String workTelephone) {
        this.workTelephone = workTelephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMissedPremiumValue() {
        return missedPremiumValue;
    }

    public String getFormattedCurrency(){

        if(currency.equals("ZAR")){
            return Constants.ZAR;
        }
        else if(currency.equals("NAD")){
            return  Constants.NAD;
        }
        else{
            return Constants.ZAR;
        }

    }

}
