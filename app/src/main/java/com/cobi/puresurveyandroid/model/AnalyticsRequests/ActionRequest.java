package com.cobi.puresurveyandroid.model.AnalyticsRequests;

public class ActionRequest {

    public String DeviceId;
    public int ActionType;
    public String AdditionalInfo;

    public ActionRequest(String deviceId, int actionType, String additionalInfo) {
        DeviceId = deviceId;
        ActionType = actionType;
        AdditionalInfo = additionalInfo;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public int getActionType() {
        return ActionType;
    }

    public void setActionType(int actionType) {
        ActionType = actionType;
    }

    public String getAdditionalInfo() {
        return AdditionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        AdditionalInfo = additionalInfo;
    }
}
