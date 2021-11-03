package com.cobi.puresurveyandroid.model;

/**
 * Created by admin on 2017/10/04.
 */

public class UserCustomerEventsRequest {
    private String guid;
    private String since;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }
}
