package com.cobi.puresurveyandroid.model.sales;

import com.cobi.puresurveyandroid.model.BaseResponse;

import java.util.ArrayList;

public class CustomerEventsResponse extends BaseResponse {
    private ArrayList<CustomerEvents> data;

    public ArrayList<CustomerEvents> getItems() {
        return data;
    }

    public void setItems(ArrayList<CustomerEvents> items) {
        this.data = items;
    }
}