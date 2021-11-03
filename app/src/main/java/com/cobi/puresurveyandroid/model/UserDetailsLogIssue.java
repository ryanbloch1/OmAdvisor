package com.cobi.puresurveyandroid.model;

public class UserDetailsLogIssue {

    String fullName;
    String email;
    String mobile;

    public UserDetailsLogIssue(String fullName,  String email, String mobile) {

        this.email = email;
        this.fullName = fullName;
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMobile() {
        return mobile;
    }
}
