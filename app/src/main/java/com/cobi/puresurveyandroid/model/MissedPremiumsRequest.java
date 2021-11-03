package com.cobi.puresurveyandroid.model;

import com.cobi.puresurveyandroid.util.PreferencesHelper;

public class MissedPremiumsRequest {
    public String imedCode;

    public MissedPremiumsRequest(String salesCode){
        imedCode = salesCode;
    }
}
