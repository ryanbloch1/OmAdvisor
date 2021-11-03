
        package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Needs {

    @SerializedName("astute")
    @Expose
    private String astute;
    @SerializedName("astuteDate")
    @Expose
    private String astuteDate;

    public String getAstute() {
        return astute;
    }

    public void setAstute(String astute) {
        this.astute = astute;
    }

    public String getAstuteDate() {
        return astuteDate;
    }

    public void setAstuteDate(String astuteDate) {
        this.astuteDate = astuteDate;
    }

}