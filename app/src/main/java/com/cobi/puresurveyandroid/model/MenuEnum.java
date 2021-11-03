package com.cobi.puresurveyandroid.model;

public enum MenuEnum {
    VERSION_POSITION(0),
    PROFILE_POSITION(1),
    SEGMENT_POSITION(2),
    SWITCH_USER_POSITION(3),
    RATE_APP(4),
    CPD_POINTS_POSITION(5),
    HELP_POSITION(6),
    OMTC_POSITION(7),
    LOG_ISSUE_POS(8),
    LOG_ISSUE_POS_BASIC(9),
    NOTIFICATION_MANAGER(10),
    LOGOUT_POSITION(11);

    MenuEnum(int numValue) {
        this.numVal = numValue;
    }

    public int getNumVal() {
        return numVal;
    }

    final int numVal;

}
