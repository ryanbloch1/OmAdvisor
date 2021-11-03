package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.SerializedName;

public class key {

    @SerializedName("key")
    public String key;

    public key(String mkey) {
        this.key = mkey;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
