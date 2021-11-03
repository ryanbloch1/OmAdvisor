package com.cobi.puresurveyandroid.model;

import java.util.List;

public class CampaignResponse {
    List<Campaign> campaigns;

    public CampaignResponse(List<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    public List<Campaign> getCampaigns() {
        return campaigns;
    }
}
