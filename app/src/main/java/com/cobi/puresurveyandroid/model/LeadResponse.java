package com.cobi.puresurveyandroid.model;

import java.util.List;

public class LeadResponse {
    List<Lead> leads;

    public LeadResponse(List<Lead> leads){
        this.leads = leads;
    }

    public List<Lead> getLeads() {
        return leads;
    }
}
