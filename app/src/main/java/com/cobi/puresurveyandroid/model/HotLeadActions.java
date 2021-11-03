package com.cobi.puresurveyandroid.model;

public enum HotLeadActions {
    Accept(0),
    Refer(1),
    Reject(2);

    HotLeadActions(int numValue) {
        this.numVal = numValue;
    }

    public int getNumVal() {
        return numVal;
    }

    final int numVal;
}
