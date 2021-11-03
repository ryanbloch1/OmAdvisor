package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ChangePasswordRequest {

    private String changePasswordAtNextLogin;

    private String password;

    private String username;

    public String getChangePasswordAtNextLogin() {
        return changePasswordAtNextLogin;
    }

    public void setChangePasswordAtNextLogin(String changePasswordAtNextLogin) {
        this.changePasswordAtNextLogin = changePasswordAtNextLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ChangePasswordRequest(String changePasswordAtNextLogin, String password, String username) {
        this.changePasswordAtNextLogin = changePasswordAtNextLogin;
        this.password = password;
        this.username = username;
    }



}
