package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsistsOf {

    public ConsistsOf(String keyName, String keyValue) {
        this.keyName = keyName;
        this.keyValue = keyValue;
    }

    @SerializedName("keyName")
    @Expose
    private String keyName;
    @SerializedName("keyValue")
    @Expose
    private String keyValue;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

}
