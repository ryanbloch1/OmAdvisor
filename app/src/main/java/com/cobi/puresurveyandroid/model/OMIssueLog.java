package com.cobi.puresurveyandroid.model;

import org.joda.time.DateTime;

import java.util.Date;

public class OMIssueLog {

    public String SalesCode;
    public String Name;
    public String EmailAddress;
    public String ContactNumber;
    public String Enviroment;
    public String DateOccured;
    public String Internet;
    public String ProcessIssue;
    public String ProcessDescription;
    public String AdditionalInfo;
    public String AdditionalInfoComment;
    public String Screenshot;
    public String Comments;

    public OMIssueLog(String salesCode, String name, String emailAddress, String contactNumber, String enviroment, String dateOccured, String internet, String processIssue, String processDescription, String additionalInfo, String additionalInfoComment, String screenshot, String comments) {
        SalesCode = salesCode;
        Name = name;
        EmailAddress = emailAddress;
        ContactNumber = contactNumber;
        Enviroment = enviroment;
        DateOccured = dateOccured;
        Internet = internet;
        ProcessIssue = processIssue;
        ProcessDescription = processDescription;
        AdditionalInfo = additionalInfo;
        AdditionalInfoComment = additionalInfoComment;
        Screenshot = screenshot;
        Comments = comments;
    }

    public String getSalesCode() {
        return SalesCode;
    }

    public String getName() {
        return Name;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public String getEnviroment() {
        return Enviroment;
    }

    public String getDateOccured() {
        return DateOccured;
    }

    public String getInternet() {
        return Internet;
    }

    public String getProcessIssue() {
        return ProcessIssue;
    }

    public String getProcessDescription() {
        return ProcessDescription;
    }

    public String getAdditionalInfo() {
        return AdditionalInfo;
    }

    public String getAdditionalInfoComment() {
        return AdditionalInfoComment;
    }

    public String getScreenshot() {
        return Screenshot;
    }

    public String getComments() {
        return Comments;
    }
}
