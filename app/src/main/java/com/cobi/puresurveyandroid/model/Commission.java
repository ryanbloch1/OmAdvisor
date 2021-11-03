package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Commission {

    @SerializedName("Currency")
    @Expose
    private String currency;

    @SerializedName("MonthToDateConfirmedTotal")
    @Expose
    private Integer monthtoDateTotal;

    @SerializedName("MonthToDateUnconfirmedTotal")
    @Expose
    private Integer monthToDateTotalUnconfirmedTotal;

    @SerializedName("MonthToDateIssuedSalesTotal")
    @Expose
    private Integer monthToDateIssuedSalesTotal;


    @SerializedName("PreviousMonthTotal")
    @Expose
    private Integer previousMonthTotal;

    public Integer getMonthToDateTotalUnconfirmedTotal() {
        return monthToDateTotalUnconfirmedTotal;
    }

    public void setMonthToDateTotalUnconfirmedTotal(Integer monthToDateTotalUnconfirmedTotal) {
        this.monthToDateTotalUnconfirmedTotal = monthToDateTotalUnconfirmedTotal;
    }

    public Integer getMonthToDateIssuedSalesTotal() {
        return monthToDateIssuedSalesTotal;
    }

    public void setMonthToDateIssuedSalesTotal(Integer monthToDateIssuedSalesTotal) {
        this.monthToDateIssuedSalesTotal = monthToDateIssuedSalesTotal;
    }

    private String code;
    private String level;
    private String message;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getMonthtoDateTotal() {
        return monthtoDateTotal;
    }

    public void setMonthtoDateTotal(Integer monthtoDateTotal) {
        this.monthtoDateTotal = monthtoDateTotal;
    }

    public Integer getPreviousMonthTotal() {
        return previousMonthTotal;
    }

    public void setPreviousMonthTotal(Integer previousMonthTotal) {
        this.previousMonthTotal = previousMonthTotal;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
