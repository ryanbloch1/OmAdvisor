package com.cobi.puresurveyandroid.model;

public class MissedPremiumContract {


    public String contractNumber;
    public String product;
    public String dueDate;
    public String coolOffDate;
    public int clawBack;

    public MissedPremiumContract(String contractNumber, String product, String dueDate, String coolOffDate, int clawBack) {
        this.contractNumber = contractNumber;
        this.product = product;
        this.dueDate = dueDate;
        this.coolOffDate = coolOffDate;
        this.clawBack = clawBack;
    }


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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCoolOffDate() {
        return coolOffDate;
    }

    public void setCoolOffDate(String coolOffDate) {
        this.coolOffDate = coolOffDate;
    }

    public int getClawBack() {
        return clawBack;
    }

    public void setClawBack(int clawBack) {
        this.clawBack = clawBack;
    }
}
