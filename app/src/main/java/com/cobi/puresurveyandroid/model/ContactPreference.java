
package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactPreference {

    @SerializedName("usage")
    @Expose
    private String usage;
    @SerializedName("ContactPointInPreference")
    @Expose
    private ContactPointInPreference contactPointInPreference;

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public ContactPointInPreference getContactPointInPreference() {
        return contactPointInPreference;
    }

    public void setContactPointInPreference(ContactPointInPreference contactPointInPreference) {
        this.contactPointInPreference = contactPointInPreference;
    }

}
