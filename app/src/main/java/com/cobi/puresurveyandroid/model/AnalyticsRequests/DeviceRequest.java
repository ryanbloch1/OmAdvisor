package com.cobi.puresurveyandroid.model.AnalyticsRequests;

public class DeviceRequest {
    private String Identifier;
    private String Manufacturer;
    private String Model;

    public DeviceRequest(String identifier, String manufacturer, String model) {
        Identifier = identifier;
        Manufacturer = manufacturer;
        Model = model;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public String getModel() {
        return Model;
    }
}
