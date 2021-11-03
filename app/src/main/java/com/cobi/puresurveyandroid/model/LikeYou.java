
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeYou {

    @SerializedName("peopleLikeYou1")
    @Expose
    private String peopleLikeYou1;
    @SerializedName("peopleLikeYou2")
    @Expose
    private String peopleLikeYou2;
    @SerializedName("peopleLikeYou3")
    @Expose
    private String peopleLikeYou3;
    @SerializedName("peopleLikeYou4")
    @Expose
    private String peopleLikeYou4;
    @SerializedName("peopleLikeYou5")
    @Expose
    private String peopleLikeYou5;

    public String getPeopleLikeYou1() {
        return peopleLikeYou1;
    }

    public void setPeopleLikeYou1(String peopleLikeYou1) {
        this.peopleLikeYou1 = peopleLikeYou1;
    }

    public String getPeopleLikeYou2() {
        return peopleLikeYou2;
    }

    public void setPeopleLikeYou2(String peopleLikeYou2) {
        this.peopleLikeYou2 = peopleLikeYou2;
    }

    public String getPeopleLikeYou3() {
        return peopleLikeYou3;
    }

    public void setPeopleLikeYou3(String peopleLikeYou3) {
        this.peopleLikeYou3 = peopleLikeYou3;
    }

    public String getPeopleLikeYou4() {
        return peopleLikeYou4;
    }

    public void setPeopleLikeYou4(String peopleLikeYou4) {
        this.peopleLikeYou4 = peopleLikeYou4;
    }

    public String getPeopleLikeYou5() {
        return peopleLikeYou5;
    }

    public void setPeopleLikeYou5(String peopleLikeYou5) {
        this.peopleLikeYou5 = peopleLikeYou5;
    }

}