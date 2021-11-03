package com.cobi.puresurveyandroid.model;

/**
 * Created by admin on 2017/10/04.
 */

public class UpdateDeviceRequest extends RegisterRequest {
    private String guid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
