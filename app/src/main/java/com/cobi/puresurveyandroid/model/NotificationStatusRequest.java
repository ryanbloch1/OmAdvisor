package com.cobi.puresurveyandroid.model;

public class NotificationStatusRequest {

    String StaffId;
    String NotificationId;
    boolean IsOptIn;

    public NotificationStatusRequest(String staffId, String notificationId, boolean isOptIn) {
        StaffId = staffId;
        NotificationId = notificationId;
        this.IsOptIn = isOptIn;
    }

    public String getStaffId() {
        return StaffId;
    }

    public String getNotificationId() {
        return NotificationId;
    }

    public boolean isOptIn() {
        return IsOptIn;
    }
}
