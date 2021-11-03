package com.cobi.puresurveyandroid.model.sales;

import com.cobi.puresurveyandroid.model.BaseResponse;
import com.cobi.puresurveyandroid.model.Lead;

import java.util.ArrayList;

public class LeadsResponse extends BaseResponse {
    private ArrayList<Lead> data;

    public ArrayList<Lead> getItems() {
        return data;
    }

    public void setItems(ArrayList<Lead> items) {
        this.data = items;
    }
}