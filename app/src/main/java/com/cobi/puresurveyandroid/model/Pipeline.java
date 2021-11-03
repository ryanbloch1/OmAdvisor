
package com.cobi.puresurveyandroid.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pipeline{

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("intermediary_code")
    @Expose
    private Integer intermediaryCode;
    @SerializedName("contracting_party")
    @Expose
    private String contractingParty;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getIntermediaryCode() {
        return intermediaryCode;
    }

    public void setIntermediaryCode(Integer intermediaryCode) {
        this.intermediaryCode = intermediaryCode;
    }

    public String getContractingParty() {
        return contractingParty;
    }

    public void setContractingParty(String contractingParty) {
        this.contractingParty = contractingParty;
    }
}
