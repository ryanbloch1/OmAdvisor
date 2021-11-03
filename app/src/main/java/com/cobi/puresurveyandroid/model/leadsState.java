package com.cobi.puresurveyandroid.model;

public enum leadsState {
    EXPIRY(0), NOEXPIRY(1), OTHER(2);

    private final int value;

    leadsState(int value) {
        this.value = value;
    }
}
