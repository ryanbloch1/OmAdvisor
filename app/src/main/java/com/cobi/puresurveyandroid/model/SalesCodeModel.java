package com.cobi.puresurveyandroid.model;

public class SalesCodeModel
{

    private String salesCodeName;
    private String segmentChannelName;


    public SalesCodeModel(String salesCodeName, String segmentChannelName) {
        this.salesCodeName = salesCodeName;
        this.segmentChannelName = segmentChannelName;
    }

    public String getSalesCodeName() {
        return salesCodeName;
    }

    public void setSalesCodeName(String salesCodeName) {
        this.salesCodeName = salesCodeName;
    }

    public String getSegmentChannelName() {
        return segmentChannelName;
    }

    public void setSegmentChannelName(String segmentChannelName) {
        this.segmentChannelName = segmentChannelName;
    }

}
