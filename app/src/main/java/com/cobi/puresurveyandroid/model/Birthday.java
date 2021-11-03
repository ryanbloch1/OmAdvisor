
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Birthday {

    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("PsiCode")
    @Expose
    private String psiCode;
    @SerializedName("GCSId")
    @Expose
    private String gCSId;
    @SerializedName("DateOfBirth")
    @Expose
    private String dateOfBirth;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPsiCode() {
        return psiCode;
    }

    public void setPsiCode(String psiCode) {
        this.psiCode = psiCode;
    }

    public String getGCSId() {
        return gCSId;
    }

    public void setGCSId(String gCSId) {
        this.gCSId = gCSId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
