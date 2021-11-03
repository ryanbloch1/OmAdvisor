package com.cobi.puresurveyandroid.model.AnalyticsRequests;

public enum ActionTypes {

    Login(1),
    AppShare(2),
    CpdAccessed(3),
    HelpPageAccessed(4),
    TAndCAccessed(5),
    OnboardingAccessed(6),
    LeadsAccessed(7),
    LeadCampaignAccessed(8),
    LeadCustomerAccessed(9),
    LeadContactPhone(10),
    LeadContactEmail(11),
    LeadContactSms(12),
    BirthdaysAccessed(13),
    BirthdayCustomerAccessed(14),
    BirthdayContactPhone(15),
    BirthdayContactEmail(16),
    BirthdayContactSms(17),
    MissedPremiumsAccessed(18),
    MissedPremiumsCustomerAccessed(19),
    MissedPremiumsContactPhone(20),
    MissedPremiumsContactEmail(21),
    MissedPremiumsContactSms(22),
    LogOut(23),
    PipelineAccessed(24),
    PipelineCustomerAccessed(25),
    EventsAccessed(26),
    SalesEarningsAccessed(27),
    SalesPersonalTargetEdit(28),
    HelpPagePhone(29),
    MyCustomerAccessed(30),
    TAndCEmailContacted(31),
    LogIssueAccessed(32),
    LogIssueSubmission(33),
    MissedPremiumContractsAccessed(34),
    HelpPageEmail(35),
    LeadAccepted(36),
    LeadReferred(37),
    LeadDeclined(38);

    ActionTypes(int numValue) {
        this.numVal = numValue;
    }

    public int getNumVal() {
        return numVal;
    }

    private int numVal;
}
