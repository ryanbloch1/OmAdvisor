package com.cobi.puresurveyandroid.model;

import java.util.List;

public class MissedPremiumResponse {
    private List<MissedPremium> missedPremiums;

    public MissedPremiumResponse(List<MissedPremium> missedPremiums) {
        this.missedPremiums = missedPremiums;
    }

    public List<MissedPremium> getMissedPremiums() {
        return missedPremiums;
    }
}
