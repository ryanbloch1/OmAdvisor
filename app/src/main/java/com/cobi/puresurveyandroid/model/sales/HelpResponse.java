package com.cobi.puresurveyandroid.model.sales;

import com.cobi.puresurveyandroid.model.BaseResponse;

import java.util.ArrayList;

public class HelpResponse extends BaseResponse {
    private ArrayList<Help> data;

    public ArrayList<Help> getItems() {
        return data;
    }

    public void setItems(ArrayList<Help> items) {
        this.data = items;
    }
}