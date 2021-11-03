package com.cobi.puresurveyandroid.model.sales;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerEvents implements Serializable, Parcelable {

    public static final Creator<CustomerEvents> CREATOR = new Creator<CustomerEvents>() {
        @Override
        public CustomerEvents createFromParcel(Parcel source) {
            return new CustomerEvents(source);
        }

        @Override
        public CustomerEvents[] newArray(int size) {
            return new CustomerEvents[size];
        }
    };
    @SerializedName("name")
    private String name;
    @SerializedName("day")
    private int day;
    @SerializedName("month")
    private int month;
    @SerializedName("home")
    private String home;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("email")
    private String email;

    protected CustomerEvents(Parcel in) {
        this.name = in.readString();
        this.day = in.readInt();
        this.month = in.readInt();
        this.home = in.readString();
        this.mobile = in.readString();
        this.email = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.day);
        dest.writeInt(this.month);
        dest.writeString(this.home);
        dest.writeString(this.mobile);
        dest.writeString(this.email);
    }
}
