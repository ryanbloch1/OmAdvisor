package com.cobi.puresurveyandroid.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by admin on 2018/03/29.
 */

public class MatrixItem {
    private String url; //For use with the 'web' type to indicate its URL to show
    @SerializedName("icon_url")
    private String iconUrl; //Full URL for the icon to show on the cell
    private String title; //The title for the cell shown under its icon
    private String type; //The type of cell this is, one of [ web, inbox, sales ]
    @SerializedName("badge_count")
    private int badgeCount; //Number of unseen notifications
    @SerializedName("active")
    private boolean active; //If this cell is mean to be enabled or disabled (greyed out)
    @SerializedName("default")
    private boolean launchDefault; //If this is the default item, meaning the app must show this cell on launch instead of the dashboard. Only one item is meant to be default. App must open the first item in the matrix that is default
    @SerializedName("onboarding")
    private ArrayList<OnboardingInfo> onboarding; //If this is empty or 'null' then there is no onboarding to show for this cell. See Onboarding item payload for more information

    public MatrixItem(String url, String iconUrl, String title, int badgeCount, String type, ArrayList<OnboardingInfo> onboarding) {
        this.url = url;
        this.iconUrl = iconUrl;
        this.title = title;
        this.badgeCount = badgeCount;
        this.type = type;
        this.onboarding = onboarding;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBadgeCount() {
        return badgeCount;
    }

    public void setBadgeCount(int badgeCount) {
        this.badgeCount = badgeCount;
    }

    public boolean getActive() {
        //FIXME - changed for v1.0.3 release
        //FIXME - return active;
        return !(type.equals("web") && TextUtils.isEmpty(url));
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getLaunchDefault() {
        return launchDefault;
    }

    public void setLaunchDefault(boolean launchDefault) {
        this.launchDefault = launchDefault;
    }

    public ArrayList<OnboardingInfo> getOnboardingList() {
        return onboarding;
    }

    public void setOnboardingList(ArrayList<OnboardingInfo> onboarding) {
        this.onboarding = onboarding;
    }
}
