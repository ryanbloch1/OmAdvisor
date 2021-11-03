
package com.cobi.puresurveyandroid.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntermediaryAgreement {

    @SerializedName("inceptionDate")
    @Expose
    private String inceptionDate;
    @SerializedName("externalReference")
    @Expose
    private String externalReference;
    @SerializedName("IntermediaryAgreementSpecification")
    @Expose
    private IntermediaryAgreementSpecification intermediaryAgreementSpecification;
    @SerializedName("ChannelRole")
    @Expose
    private List<ChannelRole> channelRole = null;

    public String getInceptionDate() {
        return inceptionDate;
    }

    public void setInceptionDate(String inceptionDate) {
        this.inceptionDate = inceptionDate;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public IntermediaryAgreementSpecification getIntermediaryAgreementSpecification() {
        return intermediaryAgreementSpecification;
    }

    public void setIntermediaryAgreementSpecification(IntermediaryAgreementSpecification intermediaryAgreementSpecification) {
        this.intermediaryAgreementSpecification = intermediaryAgreementSpecification;
    }

    public List<ChannelRole> getChannelRole() {
        return channelRole;
    }

    public void setChannelRole(List<ChannelRole> channelRole) {
        this.channelRole = channelRole;
    }

}
