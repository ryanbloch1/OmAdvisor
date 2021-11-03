package com.cobi.puresurveyandroid.model;

import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.google.gson.JsonObject;

public class IntermediaryDetailsRequest {

    public IntermediaryDetailsRequest(String digitalId){
        IntermediarySearchCriteria = new SearchCriteria(digitalId);
    }

    public SearchCriteria IntermediarySearchCriteria ;

    public class SearchCriteria{
        public SearchCriteria(String digitalId){
            this.digitalId = digitalId;
        }
        public String digitalId;
    }
}
