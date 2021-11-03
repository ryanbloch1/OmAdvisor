
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartySearchRequest {

    @SerializedName("PartySearchCriteria")
    @Expose
    private PartySearchCriteria partySearchCriteria;

    public PartySearchCriteria getPartySearchCriteria() {
        return partySearchCriteria;
    }

    public void setPartySearchCriteria(PartySearchCriteria partySearchCriteria) {
        this.partySearchCriteria = partySearchCriteria;
    }

    public PartySearchRequest(PartySearchCriteria partySearchCriteria) {
        this.partySearchCriteria = partySearchCriteria;
    }
}
