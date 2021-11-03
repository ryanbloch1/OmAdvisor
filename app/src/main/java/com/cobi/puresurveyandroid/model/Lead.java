
package com.cobi.puresurveyandroid.model;

import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.util.cPriority;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Lead {





    private leadsState state;

    private String mobileNo;
    private String homeNo;
    private String busNo;

    int index;

    public int getIndex() {
        return index;
    }

    private  boolean test;

    public boolean isTest() {
        return test;
    }

    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("GCSId")
    @Expose
    private String gCSId;
    @SerializedName("LeadId")
    @Expose
    private long leadId;
    @SerializedName("CurrentState")
    @Expose
    private String currentState;
    @SerializedName("Classification")
    @Expose
    private String classification;
    @SerializedName("LeadExpiryDate")
    @Expose
    private String leadExpiryDate;

    public long getLeadId() {
        return leadId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGCSId() {
        return gCSId;
    }

    public void setGCSId(String gCSId) {
        this.gCSId = gCSId;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getLeadExpiryDate() {
        return leadExpiryDate;
    }

    public void setLeadExpiryDate(String leadExpiryDate) {
        this.leadExpiryDate = leadExpiryDate;
    }

    public leadsState getState() {
        return state;
    }

    public void setState(leadsState state) {
        this.state = state;
    }

    public static Comparator<Lead> pComparator = new Comparator<Lead>() {

        public int compare(Lead s1, Lead s2) {

            if (s1.getState() == s2.getState()) {

                if (s1.getState() == leadsState.EXPIRY) {

                    return DateHelper.StringToDate(s1.getLeadExpiryDate()).compareTo(DateHelper.StringToDate(s2.getLeadExpiryDate()));

                } else {
                    return s1.getFullName().compareTo(s2.getFullName());
                }


            } else {
                return s1.getState().compareTo(s2.getState());
            }

        }
    };


    public Lead(String fullName, long leadId, String currentState, String homeNo, String busNo, String mobileNo, boolean test, int index) {
        this.fullName = fullName;
        this.leadId = leadId;
        this.currentState = currentState;
        this.homeNo = homeNo;
        this.busNo = busNo;
        this.mobileNo = mobileNo;
        this.test = test;
        this.index = index;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getHomeNo() {
        return homeNo;
    }

    public String getBusNo() {
        return busNo;
    }
}
