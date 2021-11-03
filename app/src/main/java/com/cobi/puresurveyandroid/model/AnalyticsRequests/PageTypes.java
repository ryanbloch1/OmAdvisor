package com.cobi.puresurveyandroid.model.AnalyticsRequests;

public enum PageTypes {


    General(1),
    Commission(2),
    Leads(3),
    Pipelines(4),
    Events(5),
    Enhancements(6),
    MissedPremiums(7);


    PageTypes(int numValue) {
        this.numVal = numValue;
    }

    public int getNumVal() {
        return numVal;
    }

    private int numVal;




}
