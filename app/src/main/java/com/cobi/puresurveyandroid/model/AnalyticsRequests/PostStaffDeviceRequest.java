package com.cobi.puresurveyandroid.model.AnalyticsRequests;

public class PostStaffDeviceRequest {


    private String StaffId;
    private String DeviceId;

    public PostStaffDeviceRequest(String staffId, String deviceId) {
        StaffId = staffId;
        DeviceId = deviceId;
    }

    public String getStaffId() {
        return StaffId;
    }

    public String getDeviceId() {
        return DeviceId;
    }
}
