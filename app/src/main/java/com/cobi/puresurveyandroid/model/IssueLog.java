package com.cobi.puresurveyandroid.model;

import java.util.Date;

public class IssueLog {

    public String SalesCode;
    public String EmailAddress;
    public String ContactNumber;
    public String DateOccured;
    public String Comments;
    public String AdvisorAppIssue;

    public IssueLog(String salesCode, String emailAddress, String contactNumber, String dateOccured, String comments, String advisorAppIssue) {
        SalesCode = salesCode;
        EmailAddress = emailAddress;
        ContactNumber = contactNumber;
        DateOccured = dateOccured;
        Comments = comments;
        AdvisorAppIssue = advisorAppIssue;
    }

    public String getSalesCode() {
        return SalesCode;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public String getDateOccured() {
        return DateOccured;
    }

    public String getComments() {
        return Comments;
    }

    public String getAdvisorAppIssue() {
        return AdvisorAppIssue;
    }
}
