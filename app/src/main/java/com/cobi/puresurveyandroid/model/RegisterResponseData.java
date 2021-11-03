package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/10/04.
 */

public class RegisterResponseData {
    private String guid;
    @SerializedName("upgrade_version")
    private String upgradeVersion;
    @SerializedName("mandatory_upgrade_version")
    private String mandatoryUpgradeVersion;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUpgradeVersion() {
        return upgradeVersion;
    }

    public void setUpgradeVersion(String upgradeVersion) {
        this.upgradeVersion = upgradeVersion;
    }

    public String getMandatoryUpgradeVersion() {
        return mandatoryUpgradeVersion;
    }

    public void setMandatoryUpgradeVersion(String mandatoryUpgradeVersion) {
        this.mandatoryUpgradeVersion = mandatoryUpgradeVersion;
    }
}
