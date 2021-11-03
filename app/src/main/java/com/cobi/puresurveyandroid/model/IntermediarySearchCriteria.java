
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntermediarySearchCriteria {


    @SerializedName("digitalId")
    private String digitalId;

    public IntermediarySearchCriteria(String digitalId)
    {
        this.digitalId = digitalId;
    }



}
