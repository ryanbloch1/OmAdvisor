package com.cobi.puresurveyandroid.model.sales;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Help implements Serializable, Parcelable {

    public static final Creator<Help> CREATOR = new Creator<Help>() {
        @Override
        public Help createFromParcel(Parcel source) {
            return new Help(source);
        }

        @Override
        public Help[] newArray(int size) {
            return new Help[size];
        }
    };
    @SerializedName("name")
    private String name;
    @SerializedName("number")
    private String number;

    protected Help(Parcel in) {
        this.name = in.readString();
        this.number = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.number);
    }
}
