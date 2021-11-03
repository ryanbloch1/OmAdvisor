package com.cobi.puresurveyandroid.model;

/**
 * Created by admin on 2017/10/04.
 */

public class UserCustomerEventsResponse extends BaseResponse {
    private UserCustomerEventsData data;

    public UserCustomerEventsData getData() {
        return data;
    }

    public void setData(UserCustomerEventsData data) {
        this.data = data;
    }
}
