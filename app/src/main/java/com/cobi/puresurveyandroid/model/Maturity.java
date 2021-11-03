

package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Maturity {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("staffCode")
    @Expose
    private Object staffCode;
    @SerializedName("productType")
    @Expose
    private String productType;
    @SerializedName("maturityValue")
    @Expose
    private String maturityValue;
    @SerializedName("maturityDate")
    @Expose
    private String maturityDate;
    @SerializedName("retainLikelyhood")
    @Expose
    private String retainLikelyhood;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Object getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(Object staffCode) {
        this.staffCode = staffCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getMaturityValue() {
        return maturityValue;
    }

    public void setMaturityValue(String maturityValue) {
        this.maturityValue = maturityValue;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getRetainLikelyhood() {
        return retainLikelyhood;
    }

    public void setRetainLikelyhood(String retainLikelyhood) {
        this.retainLikelyhood = retainLikelyhood;
    }

}