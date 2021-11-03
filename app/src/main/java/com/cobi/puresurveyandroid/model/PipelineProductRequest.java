package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PipelineProductRequest {

    List<key> keys;

    public List<key> getKeys() {
        return keys;
    }

    public void setKeys(List<key> keys) {
        this.keys = keys;
    }
}
