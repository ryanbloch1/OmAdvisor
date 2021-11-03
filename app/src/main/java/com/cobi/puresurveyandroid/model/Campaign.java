
package com.cobi.puresurveyandroid.model;

import com.cobi.puresurveyandroid.util.cPriority;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Campaign{

    private cPriority cPriority;

    @SerializedName("CampaignId")
    @Expose
    private Integer campaignId;
    @SerializedName("CampaignName")
    @Expose
    private String campaignName;
    @SerializedName("LoadedLeads")
    @Expose
    private Integer loadedLeads;
    @SerializedName("Priority")
    @Expose
    private String priority;
    @SerializedName("ExpiringLeads")
    @Expose
    private Integer expiringLeads;

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Integer getLoadedLeads() {
        return loadedLeads;
    }

    public void setLoadedLeads(Integer loadedLeads) {
        this.loadedLeads = loadedLeads;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getExpiringLeads() {
        return expiringLeads;
    }

    public void setExpiringLeads(Integer expiringLeads) {
        this.expiringLeads = expiringLeads;
    }

    public com.cobi.puresurveyandroid.util.cPriority getcPriority() {
        return cPriority;
    }

    public void setcPriority(com.cobi.puresurveyandroid.util.cPriority cPriority) {
        this.cPriority = cPriority;
    }



    public static Comparator<Campaign> pComparator = new Comparator<Campaign>() {

        public int compare(Campaign s1, Campaign s2) {

            if (s1.getcPriority() == s2.getcPriority()) {
                return s1.getCampaignName().compareTo(s2.getCampaignName());
            } else {
                return s1.getcPriority().compareTo(s2.getcPriority());
            }

    }};

}


