
package com.cobi.puresurveyandroid.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PingDigitalIdResponse {

    @SerializedName("sub")
    @Expose
    private String sub;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("commonname")
    @Expose
    private String commonname;
    @SerializedName("DigitalID")
    @Expose
    private String digitalID;
    @SerializedName("obpasswordchangeflag")
    @Expose
    private String obpasswordchangeflag;
    @SerializedName("ismemberof")
    @Expose
    private List<String> ismemberof = null;
    @SerializedName("uri")
    @Expose
    private String uri;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCommonname() {
        return commonname;
    }

    public void setCommonname(String commonname) {
        this.commonname = commonname;
    }

    public String getDigitalID() {
        return digitalID;
    }

    public void setDigitalID(String digitalID) {
        this.digitalID = digitalID;
    }

    public String getObpasswordchangeflag() {
        return obpasswordchangeflag;
    }

    public void setObpasswordchangeflag(String obpasswordchangeflag) {
        this.obpasswordchangeflag = obpasswordchangeflag;
    }

    public List<String> getIsmemberof() {
        return ismemberof;
    }

    public void setIsmemberof(List<String> ismemberof) {
        this.ismemberof = ismemberof;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}