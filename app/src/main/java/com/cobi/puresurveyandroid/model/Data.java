
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user")
    @Expose
    private CpdUser user;
    @SerializedName("cpd")
    @Expose
    private Cpd cpd;

    public CpdUser getUser() {
        return user;
    }

    public void setUser(CpdUser user) {
        this.user = user;
    }

    public Cpd getCpd() {
        return cpd;
    }

    public void setCpd(Cpd cpd) {
        this.cpd = cpd;
    }

}
