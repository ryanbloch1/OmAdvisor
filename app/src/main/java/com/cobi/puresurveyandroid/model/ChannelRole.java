
package com.cobi.puresurveyandroid.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChannelRole {

    @SerializedName("typeName")
    @Expose
    private String typeName;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("externalReference")
    @Expose
    private String externalReference;
    @SerializedName("externalSystemID")
    @Expose
    private String externalSystemID;
    @SerializedName("Organisation")
    @Expose
    private Organisation organisation;
    @SerializedName("MarketSegment")
    @Expose
    private List<MarketSegment> marketSegment = null;
    @SerializedName("ChannelSegment")
    @Expose
    private List<ChannelSegment> channelSegment = null;
    @SerializedName("IntermediaryCommissionSharingCategory")
    @Expose
    private List<IntermediaryCommissionSharingCategory> intermediaryCommissionSharingCategory = null;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getExternalSystemID() {
        return externalSystemID;
    }

    public void setExternalSystemID(String externalSystemID) {
        this.externalSystemID = externalSystemID;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public List<MarketSegment> getMarketSegment() {
        return marketSegment;
    }

    public void setMarketSegment(List<MarketSegment> marketSegment) {
        this.marketSegment = marketSegment;
    }

    public List<ChannelSegment> getChannelSegment() {
        return channelSegment;
    }

    public void setChannelSegment(List<ChannelSegment> channelSegment) {
        this.channelSegment = channelSegment;
    }

    public List<IntermediaryCommissionSharingCategory> getIntermediaryCommissionSharingCategory() {
        return intermediaryCommissionSharingCategory;
    }

    public void setIntermediaryCommissionSharingCategory(List<IntermediaryCommissionSharingCategory> intermediaryCommissionSharingCategory) {
        this.intermediaryCommissionSharingCategory = intermediaryCommissionSharingCategory;
    }

}
